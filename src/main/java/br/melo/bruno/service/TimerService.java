package br.melo.bruno.service;

import org.springframework.stereotype.Service;

@Service
public class TimerService implements ITimerService<ScheduleContext> {

    @Override
    public void ended(ScheduleContext _context) {
        System.out.println("ended:" + _context.ended());
    }

    @Override
    public void started(ScheduleContext _context) {
        System.out.println("started:" + _context.started());
    }

    @Override
    public void beat(ScheduleContext _context) {
        System.out.println("count:" + _context.value());
    }
}
