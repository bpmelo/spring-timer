package br.melo.bruno.service;

public class TimerSchedulerContext<T> {

    private Long time;
    private Long count;
    private Boolean started;
    private Boolean ended;
    private Boolean stopped;
    private T properties;
    private ITimerScheduler callback;

    public TimerSchedulerContext(Long _time, ITimerScheduler _callback) {
        this.time = _time;
        this.count = this.time;
        this.started = Boolean.FALSE;
        this.ended = Boolean.FALSE;
        this.stopped = Boolean.FALSE;
        this.callback = _callback;
    }

    public ITimerScheduler callback() {
        return this.callback;
    }

    public T getProperties() {
        return this.properties;
    }

    public void setProperties(T _properties) {
        this.properties = _properties;
    }

    public Boolean started() {
        return this.started;
    }

    public Boolean ended() {
        return this.ended;
    }

    public Boolean stopped() {
        return this.stopped;
    }

    public void reset(Long _time) {
        this.count = _time;
    }

    public void beat() {
        this.count -= 1;
    }

    public Long value() {
        return this.count;
    }

    public Long time() { return this.time; }

    public Boolean zeroed() {
        return this.count == 0;
    }

    public void start() {
        this.started = Boolean.TRUE;
    }

    public void end() {
        this.ended = Boolean.TRUE;
    }

    public void stop() {
        this.stopped = Boolean.TRUE;
    }

}
