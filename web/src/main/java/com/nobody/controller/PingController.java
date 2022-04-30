package com.nobody.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/*
 *
 *It is absolutely workaround. It is just for using free heroku server.
 *
 */

@RestController
@RequestMapping("/api/v1/ping")
public class PingController {

    private final Logger logger = LogManager.getLogger();

    @GetMapping
    public void ping() {
        logger.log(Level.DEBUG, "Don't sleep. Locale time is " + LocalDateTime.now());
    }
}
