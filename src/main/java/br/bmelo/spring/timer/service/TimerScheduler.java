package br.bmelo.spring.timer.service;

import org.springframework.stereotype.Service;

@Service
public class TimerScheduler implements ITimerScheduler {

    @Override
    public void ended(TimerSchedulerContext _context) {
        System.out.println("ended:" + _context.ended());
    }

    @Override
    public void stopped(TimerSchedulerContext _context) {
        System.out.println("stopped:" + _context.stopped());
    }

    @Override
    public void started(TimerSchedulerContext _context) {
        System.out.println("started:" + _context.started());
    }

    @Override
    public void beat(TimerSchedulerContext _context) {
        System.out.println("count:" + _context.value());
    }
}
