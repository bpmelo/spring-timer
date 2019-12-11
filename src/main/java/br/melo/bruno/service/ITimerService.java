package br.melo.bruno.service;

public interface ITimerService<T> {
    public void ended(T _context);
    public void started(T _context);
    public void beat(T _context);
}
