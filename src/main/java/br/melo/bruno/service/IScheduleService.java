package br.melo.bruno.service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.UUID;

public interface IScheduleService {

    public ScheduleContext context(@NotNull UUID _uuid);

    public UUID schedule(@NotNull ScheduleSettings _settings) throws ParseException;

    public UUID restart(@NotNull UUID _uuid, @NotNull ScheduleSettings _settings);

    public void stop(@NotNull UUID _uuid);

}
