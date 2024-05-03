package com.pm.api;

import com.pm.dto.PositionDto;
import com.pm.service.PositionService;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Position REST Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-02
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-02          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/position")
public class PositionRestController {
    private final PositionService positionService;
    public PositionRestController(PositionService positionService) {
        this.positionService = positionService;
    }

    // 직급 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "name") String name,
                             @RequestParam(required = false, value = "order_number") Integer orderNumber,
                             HttpServletRequest request) {
        if(name == null || name.isEmpty()) {
            return new ResponseData(false, "직급명을 입력해주세요.", null);
        }

        PositionDto positionDto;
        if(idx == null) {
            positionDto = PositionDto.builder()
                    .name(name)
                    .orderNumber(orderNumber)
                    .register(1L)
                    .build();
        } else {
            positionDto = positionService.getOne(idx);
            positionDto.setName(name);
            positionDto.setOrderNumber(orderNumber);
        }

        return new ResponseData(true, "저장되었습니다.", positionService.saveItem(positionDto));
    }
}
