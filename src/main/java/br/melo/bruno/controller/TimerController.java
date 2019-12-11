package br.melo.bruno.controller;

import br.melo.bruno.service.IScheduleService;
import br.melo.bruno.service.ScheduleSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/timer")
public class TimerController {

    @Autowired
    IScheduleService scheduleService;

    @Autowired
    SimpleDateFormat sdf;

    @GetMapping(
            path = "schedule/{startDateTime}"
    )
    public ResponseEntity<String> schedule(@PathVariable("startDateTime") String _startDateTime) throws ParseException {
        Date startDateTime = sdf.parse(_startDateTime);
        ScheduleSettings settings = new ScheduleSettings(
                startDateTime,
                30L,
                1000L
        );
        UUID uuid = scheduleService.schedule(settings);
        return new ResponseEntity(uuid.toString(), HttpStatus.OK);
    }

    @GetMapping(
            path = "restart/{uuid}/{time}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity restart(@PathVariable("uuid") UUID _uuid, @PathVariable("time") Long _time) {
        Date startDateTime = new Date();
        ScheduleSettings settings = new ScheduleSettings(
                startDateTime,
                _time,
                1000L
        );
        scheduleService.restart(_uuid, settings);
        return new ResponseEntity(HttpStatus.OK);
    }
}
