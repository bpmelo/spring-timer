package br.bmelo.spring.timer.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TimerSchedulerSettings {

    private Date startDateTime;
    // Countdown time
    private Long time;
    // Delay in ms for each heartbeat, usually 1000ms (1 second)
    private Long delay;

}
