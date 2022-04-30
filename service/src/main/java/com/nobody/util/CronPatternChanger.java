package com.nobody.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CronPatternChanger {
    private static final Logger logger = LogManager.getLogger();
    private final String HOUR_PATTERN = "0 0 * * * *";
    private final String TWO_HOURS_PATTERN = "0 0 */2 * * *";
    private final String SECOND_15_PATTERN = "*/15 * * * * *";
    private final String DEFAULT_PATTERN = "0 */30 * * * *";
    private final String HOUR = "hour";
    private final String TWO_HOUR = "2hour";
    private final String SECONDS = "seconds";
    private String pattern;

    public String setPattern(String code) {
        switch (code.toLowerCase()) {
            case (HOUR):
                pattern = HOUR_PATTERN;
                break;
            case (TWO_HOUR):
                pattern = TWO_HOURS_PATTERN;
                break;
            case (SECONDS):
                pattern = SECOND_15_PATTERN;
                break;
            default:
                pattern = DEFAULT_PATTERN;
            }
        logger.log(Level.INFO, "Pattern was changed. Current pattern is " + pattern);
        return pattern;
    }
}
