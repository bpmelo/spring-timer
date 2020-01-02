package br.bmelo.spring.timer.service;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

public interface ITimerSchedulerService {

    public Map properties(@NotNull UUID _uuid);

    public void properties(@NotNull UUID _uuid, Map _properties);

    public UUID schedule(@NotNull TimerSchedulerSettings _settings, ITimerScheduler _callback);

    public UUID restart(@NotNull UUID _uuid, @NotNull TimerSchedulerSettings _settings);

    public void stop(@NotNull UUID _uuid);

}
