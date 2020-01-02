package br.bmelo.spring.timer.service;

public interface ITimerScheduler {
    public void ended(TimerSchedulerContext _context);
    public void stopped(TimerSchedulerContext _context);
    public void started(TimerSchedulerContext _context);
    public void beat(TimerSchedulerContext _context);
}
