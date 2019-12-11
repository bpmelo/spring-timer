package br.melo.bruno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
public class ScheduleService implements IScheduleService {

    @Autowired
    TaskScheduler scheduler;

    @Autowired
    ITimerService<ScheduleContext> timerService;

    private Map<UUID, ScheduledFuture> runners = new HashMap<>();
    private Map<UUID, ScheduleContext> contexts =  new HashMap<>();

    private UUID generateUUID() {
        return UUID.randomUUID();
    }

    private void context(UUID _uuid, ScheduleContext _context) {
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

    private ScheduleContext createContext(Long _timer) {
        return new ScheduleContext(_timer);
    }


    private void notifyStarted(ScheduleContext _context) {
        if (!_context.started()) {
            _context.start();
            timerService.started(_context);
        }
    }

    private ScheduledFuture createSchedule(
            UUID _uuid,
            Date _startDateTime,
            Long _delay) {
        ScheduledFuture future = scheduler.scheduleWithFixedDelay(
                ()->{
                    ScheduleContext context = context(_uuid);
                    notifyStarted(context);
                    if (context.zeroed()) {
                        context.end();
                        timerService.ended(context);
                        cancel(_uuid);
                        removeContext(_uuid);
                    }
                    else {
                        timerService.beat(context);
                        context.beat();
                    }
                },
                _startDateTime,
                _delay
        );
        return future;
    }

    @Override
    public ScheduleContext context(@NotNull UUID _uuid) {
        return this.contexts.get(_uuid);
    }

    @Override
    public UUID restart(@NotNull UUID _uuid, @NotNull ScheduleSettings _settings) {
        cancel(_uuid);
        context(_uuid).reset(_settings.getTime());
        ScheduledFuture rescheduledFuture = this.createSchedule(_uuid, _settings.getStartDateTime(), _settings.getDelay());
        runner(_uuid, rescheduledFuture);
        return _uuid;
    }

    @Override
    public void stop(@NotNull UUID _uuid) {
        this.cancel(_uuid);
        removeContext(_uuid);
    }

    @Override
    public UUID schedule(@NotNull ScheduleSettings _settings) throws ParseException {
        UUID uuid = generateUUID();
        context(uuid, createContext(_settings.getTime()));
        ScheduledFuture future = createSchedule(uuid, _settings.getStartDateTime(), _settings.getDelay());
        runner(uuid, future);
        return uuid;
    }

}
