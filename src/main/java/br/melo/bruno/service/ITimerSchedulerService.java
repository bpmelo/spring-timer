package br.melo.bruno.service;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface ITimerSchedulerService<T> {

    public TimerSchedulerContext<T> context(@NotNull UUID _uuid);

    public UUID schedule(@NotNull TimerSchedulerSettings _settings, ITimerScheduler _callback);

    public UUID restart(@NotNull UUID _uuid, @NotNull TimerSchedulerSettings _settings);

    public void stop(@NotNull UUID _uuid);

}
