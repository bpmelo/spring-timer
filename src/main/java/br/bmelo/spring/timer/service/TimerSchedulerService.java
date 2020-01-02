package br.bmelo.spring.timer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
public class TimerSchedulerService implements ITimerSchedulerService {

    @Autowired
    TaskScheduler scheduler;

    private Map<UUID, ScheduledFuture> runners = new HashMap<>();
    private Map<UUID, TimerSchedulerContext> contexts =  new HashMap<>();

    private UUID generateUUID() {
        return UUID.randomUUID();
    }

    private void context(UUID _uuid, TimerSchedulerContext _context) {
        contexts.put(_uuid, _context);
    }

    private void runner(UUID _uuid, ScheduledFuture _future) {
        runners.put(_uuid, _future);
    }

    private void removeContext(UUID _uuid) {
        contexts.remove(_uuid);
    }

    private void cancel(UUID _uuid) {
        runners.get(_uuid).cancel(Boolean.TRUE);
        runners.remove(_uuid);
    }

    private TimerSchedulerContext createContext(Long _timer, ITimerScheduler _callback) {
        return new TimerSchedulerContext(_timer, _callback);
    }


    private void notifyStarted(TimerSchedulerContext _context) {
        if (!_context.started()) {
            _context.start();
            _context.callback().started(_context);
        }
    }

    private ScheduledFuture createSchedule(
            UUID _uuid,
            Date _startDateTime,
            Long _delay,
            ITimerScheduler callback) {
        ScheduledFuture future = scheduler.scheduleWithFixedDelay(
                ()->{
                    TimerSchedulerContext context = context(_uuid);
                    notifyStarted(context);
                    if (context.zeroed()) {
                        context.end();
                        callback.ended(context);
                        cancel(_uuid);
                        removeContext(_uuid);
                    }
                    else {
                        callback.beat(context);
                        context.beat();
                    }
                },
                _startDateTime,
                _delay
        );
        return future;
    }

    private TimerSchedulerContext context(@NotNull UUID _uuid) {
        return this.contexts.get(_uuid);
    }

    @Override
    public Map properties(@NotNull UUID _uuid) {
        return this.contexts.get(_uuid).getProperties();
    }

    @Override
    public void properties(@NotNull UUID _uuid, Map _properties) {
        this.contexts.get(_uuid).setProperties(_properties);
    }

    @Override
    public UUID restart(@NotNull UUID _uuid, @NotNull TimerSchedulerSettings _settings) {
        cancel(_uuid);
        context(_uuid).reset(_settings.getTime());
        ITimerScheduler callback = context(_uuid).callback();
        ScheduledFuture rescheduledFuture = this.createSchedule(_uuid, _settings.getStartDateTime(), _settings.getDelay(), callback);
        runner(_uuid, rescheduledFuture);
        return _uuid;
    }

    @Override
    public void stop(@NotNull UUID _uuid) {
        this.cancel(_uuid);
        TimerSchedulerContext _context = context(_uuid);
        _context.stop();
        _context.callback().stopped(_context);
        removeContext(_uuid);
    }

    @Override
    public UUID schedule(@NotNull TimerSchedulerSettings _settings, @NotNull ITimerScheduler _callback) {
        UUID uuid = generateUUID();
        context(uuid, createContext(_settings.getTime(), _callback));
        ScheduledFuture future = createSchedule(uuid, _settings.getStartDateTime(), _settings.getDelay(), _callback);
        runner(uuid, future);
        return uuid;
    }

}
