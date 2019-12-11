package br.melo.bruno.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ScheduleSettings {

    private Date startDateTime;
    private Long time;
    private Long delay;

}
