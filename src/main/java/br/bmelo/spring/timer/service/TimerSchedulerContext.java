package br.bmelo.spring.timer.service;

import java.util.HashMap;
import java.util.Map;

public class TimerSchedulerContext {

    private Long time;
    private Long count;
    private Boolean started;
    private Boolean ended;
    private Boolean stopped;
    private ITimerScheduler callback;
    private Map properties;

    public TimerSchedulerContext(Long _time, ITimerScheduler _callback) {
        this.time = _time;
        this.count = this.time;
        this.started = Boolean.FALSE;
        this.ended = Boolean.FALSE;
        this.stopped = Boolean.FALSE;
        this.callback = _callback;
        properties = new HashMap();
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
