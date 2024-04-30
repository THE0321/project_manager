package com.pm.values;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * pagination
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-04-30
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-04-30          HTH             최초 등록
 **/
@Getter
public class Paging {
    private final int PER_PAGE = 5;         // 페이지 당 조회할 건수
    private Long before;                    // 이전 페이지
    private Long next;                     // 다음 페이지
    private Long before_over;               // 첫 페이지
    private Long next_over;                 // 마지막 페이지
    private Long page;                      // 현재 페이지
    private List<Long> pageList = new ArrayList<>();

    // 페이지네이션
    public Paging(Long page, Long total) {
        if(page == null) page = 1L;
        Long totalPage = (total / PER_PAGE) + 1;

        // 페이지
        Long block = page / PER_PAGE;
        Long first = block * PER_PAGE + 1;
        Long last = first + PER_PAGE > totalPage ? totalPage : first + PER_PAGE;

        for(Long i = first; i < last; i++) {
            this.pageList.add(i);
        }

        // 이전 페이지
        if(page > 1L) {
            this.before = page - 1;
            this.before_over = 1L;
        }

        // 다음 페이지
        if(page < totalPage) {
            this.next = page + 1;
            this.next_over = totalPage;
        }

        this.page = page;
    }
}
