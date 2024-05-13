package com.pm.controller;

import com.pm.dto.ChattingDto;
import com.pm.dto.ChattingMemberDto;
import com.pm.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * 
 * Chat Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-13
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-13          HTH             최초 등록
 **/
@Controller
@RequestMapping("/chat")
public class ChatController extends com.pm.util.Controller {
    private final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // 채팅 상세
    @GetMapping(value = {"/", "", "/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                         HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        model.addAttribute("room_list", chatService.getList(super.getLoginData(request).getIdx()));

        if(idx == null) {
            model.addAttribute("detail", new ChattingDto());
            model.addAttribute("member_list", new ArrayList<ChattingMemberDto>());
        } else {
            model.addAttribute("detail", chatService.getOne(idx));
            model.addAttribute("member_list", chatService.getChattingMemberList(idx));
        }

        return "chatting/detail";
    }
}
