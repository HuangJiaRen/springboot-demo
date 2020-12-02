package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.sss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String HHMM = "HHmm";

    public static String toString(Date date, String format) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }
    public static Date addDays(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }
}
