package com.cgi.dentistapp;

import com.cgi.dentistapp.service.PractitionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DentistAppApplication {

    @Autowired
    private PractitionerService practitionerService;

    public static void main(String[] args) {
        SpringApplication.run(DentistAppApplication.class, args);
    }

    @PostConstruct
    public void init() {
        practitionerService.addPractitioner("Joseph Bohner");
        practitionerService.addPractitioner("Bethel Redel");
        practitionerService.addPractitioner("Elton Whitelaw");
        practitionerService.addPractitioner("Allyson Arrieta");
        practitionerService.addPractitioner("Anderson Hearne");
    }

    @Configuration
    static class MyConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addFormatters(FormatterRegistry registry) {

        }
    }
}
