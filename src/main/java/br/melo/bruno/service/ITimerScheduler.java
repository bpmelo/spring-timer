package br.melo.bruno.service;

public interface ITimerScheduler<T> {
    public void ended(TimerSchedulerContext<T> _context);
    public void stopped(TimerSchedulerContext<T> _context);
    public void started(TimerSchedulerContext<T> _context);
    public void beat(TimerSchedulerContext<T> _context);
}
