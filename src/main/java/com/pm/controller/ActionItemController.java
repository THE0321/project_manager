package com.pm.controller;

import com.pm.dto.ActionItemDto;
import com.pm.dto.ActionItemMemberDto;
import com.pm.service.ActionItemService;
import com.pm.service.DirectoryService;
import com.pm.util.DateFormat;
import com.pm.util.Paging;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;

/**
 * 
 * Action Item Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
@Controller
@RequestMapping("/action")
public class ActionItemController extends com.pm.util.Controller {
    private final DirectoryService directoryService;
    private final ActionItemService actionItemService;
    public ActionItemController(DirectoryService directoryService, ActionItemService actionItemService) {
        this.directoryService = directoryService;
        this.actionItemService = actionItemService;
    }

    // 액션 아이템 목록
    @GetMapping(value = {"/", ""})
    public String list(@RequestParam(required = false, value = "directory_idx") Long directoryIdx,
                       @RequestParam(required = false, value = "title") String title,
                       @RequestParam(required = false, value = "status_idx") Long statusIdx,
                       @RequestParam(required = false, value = "start_date") String startDate,
                       @RequestParam(required = false, value = "end_date") String endDate,
                       @RequestParam(required = false, value = "page") Long page,
                       HttpServletRequest request, Model model) {
        model = super.setModel(request, model);
        DateFormat dateFormat = new DateFormat();

        model.addAttribute("param_directory_idx", directoryIdx);
        model.addAttribute("param_title", title);
        model.addAttribute("param_status_idx", statusIdx);
        model.addAttribute("param_start_date", startDate);
        model.addAttribute("param_end_date", endDate);

        Date sqlStartDate = startDate == null ? null : dateFormat.parseDate(startDate);
        Date sqlEndDate = endDate == null ? null : dateFormat.parseDate(endDate);

        model.addAttribute("status_list", actionItemService.getStatus());
        model.addAttribute("directory_list", directoryService.getList(3L));
        model.addAttribute("list", actionItemService.getList(3L, directoryIdx, title, statusIdx, sqlStartDate, sqlEndDate, page));
        model.addAttribute("page", new Paging(page, actionItemService.getCount(3L, directoryIdx, title, statusIdx, sqlStartDate, sqlEndDate)));

        return "action_item/list";
    }

    // 액션 아이템 상세
    @GetMapping(value = {"/detail", "/detail/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                         HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        model.addAttribute("status_list", actionItemService.getStatus());
        model.addAttribute("directory_list", directoryService.getList(3L));

        if(idx == null) {
            model.addAttribute("detail", new ActionItemDto());
            model.addAttribute("member_list", new ArrayList<ActionItemMemberDto>());
        } else {
            model.addAttribute("detail", actionItemService.getOne(idx));
            model.addAttribute("member_list", actionItemService.getMemberList(idx));
        }

        return "action_item/detail";
    }
}
