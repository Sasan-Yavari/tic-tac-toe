package com.metronom.tictactoe.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
    private static final Logger logger = LogManager.getLogger(Util.class);

    public static int tryToInt(String input, int defaultValue) {
        int result;

        try {
            result = Integer.valueOf(input);
        } catch (Exception ex) {
            logger.trace(ex);
            result = defaultValue;
        }

        return result;
    }
}
