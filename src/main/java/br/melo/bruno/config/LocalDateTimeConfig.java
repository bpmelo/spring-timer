package br.melo.bruno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
public class LocalDateTimeConfig {
    String timezone = "America/Sao_Paulo";

    @Bean
    public SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf;
    }

    @Bean
    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone(timezone);
    }

    @PostConstruct
    void setDefaultTimeZone() {
        TimeZone.setDefault(this.getTimeZone());
    }

}
