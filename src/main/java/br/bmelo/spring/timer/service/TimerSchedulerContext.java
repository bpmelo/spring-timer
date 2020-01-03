package br.bmelo.spring.timer.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class TimerSchedulerContext {

    private AtomicLong time;
    private AtomicLong count;
    private AtomicBoolean started = new AtomicBoolean(Boolean.FALSE);
    private AtomicBoolean reStarted = new AtomicBoolean(Boolean.FALSE);
    private AtomicBoolean ended = new AtomicBoolean(Boolean.FALSE);
    private AtomicBoolean stopped = new AtomicBoolean(Boolean.FALSE);
    private ITimerScheduler callback;
    private Map properties;

    public TimerSchedulerContext(Long _time, ITimerScheduler _callback, Map _properties) {
        this.time = new AtomicLong(_time);
        this.count = new AtomicLong(_time);
        this.callback = _callback;
        properties = _properties;
    }

    public void setProperties(Map _properties) {
        properties = _properties;
    }

    public Map getProperties() {
        return properties;
    }

    public ITimerScheduler callback() {
        return this.callback;
    }

    public Boolean started() {
        return this.started.get();
    }

    public Boolean reStarted() { return this.reStarted.get(); }

    public Boolean ended() {
        return this.ended.get();
    }

    public Boolean stopped() {
        return this.stopped.get();
    }

    public void reset(Long _time) {
        this.started.set(Boolean.FALSE);
        this.reStarted.set(Boolean.TRUE);
        this.count.set(_time);
    }

    public Boolean countIsTime() {
        return this.count.get() == this.time.get();
    }

    public void beat() {
        this.count.decrementAndGet();
    }

    public Long value() {
        return this.count.get();
    }

    public Long time() { return this.time.get(); }

    public Boolean zeroed() {
        return this.count.get() == 0L;
    }

    public void start() {
        this.started.set(Boolean.TRUE);
        this.reStarted.set(Boolean.FALSE);
    }

    public void end() {
        this.ended.set(Boolean.TRUE);
    }

    public void stop() {
        this.stopped.set(Boolean.TRUE);
    }

}
