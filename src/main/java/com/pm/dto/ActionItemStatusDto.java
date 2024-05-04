package com.pm.dto;

import com.pm.entity.ActionItemStatus;
import lombok.*;

/**
 *
 * action_item_status DTO
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-04
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-04          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ActionItemStatusDto {
    private Long idx;
    private String name;

    // Entity 변환
    public ActionItemStatus toEntity() {
        return ActionItemStatus.builder()
                .idx(idx)
                .name(name)
                .build();
    }

    @Builder
    public ActionItemStatusDto(Long idx, String name) {
        this.idx = idx;
        this.name = name;
    }
}
