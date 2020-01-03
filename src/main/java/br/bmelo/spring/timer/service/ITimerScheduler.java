package br.bmelo.spring.timer.service;

import java.util.Map;

public interface ITimerScheduler {
    public void ended(Map _context);
    public void stopped(Map _context);
    public void started(Map _context);
    public void beat(Map _context);
}
