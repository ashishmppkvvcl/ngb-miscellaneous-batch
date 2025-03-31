package com.mppkvvcl.ngbmiscellaneousbatch.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class GlobalResources {

    private static final String BATCH_NAME = "NGB-MISCELLANEOUS-BATCH";

    @Value("${version}")
    private String version;

    @Value("${artifactId}")
    private String artifactId;

    public static Logger getLogger(Class className) {
        return LoggerFactory.getLogger(className);
    }

    public static final String BILL_MONTH_FORMAT = "MMM-yyyy";

    public static String getBatchName() {
        return BATCH_NAME;
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public String getProjectDetail() {
        return artifactId + "-" + version;
    }
}
