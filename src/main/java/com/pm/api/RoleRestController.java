package com.pm.api;

import com.pm.dto.RoleDto;
import com.pm.service.RoleService;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * Role REST Controller
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
@RequestMapping("/api/role")
public class RoleRestController {
    private final RoleService roleService;
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    // 역할 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "name") String name,
                             HttpServletRequest request, Model model) {
        if(name == null || name.isEmpty()) {
            return new ResponseData(false, "역할명을 입력해주세요.", null);
        }

        RoleDto roleDto = null;
        if(idx == null) {
            roleDto = RoleDto.builder()
                    .name(name)
                    .register(1L)
                    .build();
        } else {
            roleDto = roleService.getOne(idx);
            roleDto.setName(name);
        }

        return new ResponseData(true, "저장되었습니다.", roleService.saveItem(roleDto));
    }
}
