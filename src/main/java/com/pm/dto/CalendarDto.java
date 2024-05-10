package com.pm.dto;

import lombok.*;

/**
 * 
 * calendar dto
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-10
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-10          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CalendarDto {
    private Integer year;
    private Integer month;
    private Integer day;

    @Builder
    public CalendarDto(Integer year, Integer month, Integer day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
