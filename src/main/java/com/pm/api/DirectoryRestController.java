package com.pm.api;

import com.pm.dto.DirectoryDto;
import com.pm.entity.Directory;
import com.pm.service.DirectoryService;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 *
 * Directory REST Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/directory")
public class DirectoryRestController {
    private final DirectoryService directoryService;
    public DirectoryRestController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    // 디렉토리 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "title") String title,
                             @RequestParam(required = false, value = "description") String description,
                             @RequestParam(required = false, value = "status_idx") Long statusIdx,
                             HttpServletRequest request) {
        if(title == null || title.isEmpty()) {
            return new ResponseData(false, "디렉토리명을 입력해주세요.", null);
        }

        DirectoryDto directoryDto;
        if(idx == null) {
            directoryDto = DirectoryDto.builder()
                    .projectIdx(3L)
                    .title(title)
                    .description(description)
                    .statusIdx(statusIdx)
                    .register(1L)
                    .build();
        } else {
            directoryDto = directoryService.getOne(idx);
            directoryDto.setTitle(title);
            directoryDto.setDescription(description);
            directoryDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
            directoryDto.setModifier(1L);

            if(directoryDto.getStatusIdx() == null) {
                if(statusIdx != null) {
                    directoryDto.setStatusIdx(statusIdx);
                    directoryDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
                }
            } else if(!directoryDto.getStatusIdx().equals(statusIdx)) {
                directoryDto.setStatusIdx(statusIdx);
                directoryDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
            }
        }

        return new ResponseData(true, "저장되었습니다.", directoryService.saveItem(directoryDto));
    }
}
