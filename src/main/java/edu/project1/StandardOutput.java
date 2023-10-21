package edu.project1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StandardOutput implements Output {

    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public void log(String message) {
        LOGGER.info(message);
    }
}
