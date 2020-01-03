package br.bmelo.spring.timer.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TimerScheduler implements ITimerScheduler {

    @Override
    public void ended(Map _context) {
        System.out.println("ended:");
    }

    @Override
    public void stopped(Map _context) {
        System.out.println("stopped:");
    }

    @Override
    public void started(Map _context) {
        System.out.println("started:");
    }

    @Override
    public void beat(Map _context) {
        System.out.println("count:");
    }
}
