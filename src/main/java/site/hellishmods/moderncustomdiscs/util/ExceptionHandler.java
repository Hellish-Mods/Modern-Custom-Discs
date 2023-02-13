package site.hellishmods.moderncustomdiscs.util;

import site.hellishmods.digitality.digitality;

public class ExceptionHandler {
    public ExceptionHandler(Exception e, String id) {
        switch (e.getClass().getCanonicalName()) {
            case "com.google.gson.JsonSyntaxException":
                digitality.LOGGER.error("Error at disc "+id+", wrong json format!");
                break;
        }
    }
}
