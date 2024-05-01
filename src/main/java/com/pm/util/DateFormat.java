package com.pm.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
