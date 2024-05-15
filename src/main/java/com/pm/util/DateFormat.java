package com.pm.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * Date Formatter
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-01
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-01          HTH             최초 등록
 **/
public class DateFormat {
    private final SimpleDateFormat formatter;

    public DateFormat() {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    public DateFormat(String pattern) {
        formatter = new SimpleDateFormat(pattern);
    }

    // Timestamp 변환
    public Timestamp parseTimestamp(String value) {
        formatter.setLenient(false);

        try {
            Date date = formatter.parse(value);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public Timestamp parseTimestamp(Calendar value) {
        formatter.setLenient(false);
        String str = formatter.format(value.getTime());

        try {
            Date date = formatter.parse(str);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    // Date 변환
    public java.sql.Date parseDate(String value) {
        formatter.setLenient(false);

        try {
            Date date = formatter.parse(value);
            return new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    // Time 변환
    public Time parseTime(String value) {
        formatter.setLenient(false);

        try {
            long time = formatter.parse(value).getTime();
            return new Time(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
