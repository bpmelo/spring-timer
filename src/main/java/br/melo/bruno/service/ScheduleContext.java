package br.melo.bruno.service;

public class ScheduleContext {

    private Long time;
    private Long count;
    private Boolean started;
    private Boolean ended;

    public ScheduleContext(Long _time) {
        this.time = _time;
        this.count = this.time;
        this.started = Boolean.FALSE;
        this.ended = Boolean.FALSE;
    }

    public Boolean started() {
        return this.started;
    }

    public Boolean ended() {
        return this.ended;
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

}
