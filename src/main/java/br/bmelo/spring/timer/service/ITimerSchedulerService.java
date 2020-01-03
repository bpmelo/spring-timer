package br.bmelo.spring.timer.service;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

public interface ITimerSchedulerService {

    public Map properties(@NotNull UUID _uuid);

    public UUID schedule(@NotNull TimerSchedulerSettings _settings, ITimerScheduler _callback, @NotNull Map _properties);

    public UUID restart(@NotNull UUID _uuid, @NotNull TimerSchedulerSettings _settings);

    public void stop(@NotNull UUID _uuid);

}
