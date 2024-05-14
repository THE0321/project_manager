package com.pm.api;

import com.pm.dto.ChattingDto;
import com.pm.dto.ChattingRoomDto;
import com.pm.service.ChatService;
import com.pm.service.FileService;
import com.pm.util.Controller;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * 
 * Chat REST Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-14
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-14          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/chat")
public class ChatRestController extends Controller {
    private final ChatService chatService;
    private final FileService fileService;

    public ChatRestController(ChatService chatService, FileService fileService) {
        this.chatService = chatService;
        this.fileService = fileService;
    }

    // 채팅방 저장
    @PostMapping("/save_room")
    public ResponseData saveRoom(@RequestParam(required = false, value = "idx") Long idx,
                                 @RequestParam(required = false, value = "title") String title,
                                 @RequestParam(required = false, value = "member_list") Long[] memberList,
                                 @RequestParam(required = false, value = "delete_list") Long[] deleteMemberList,
                                 HttpServletRequest request) {
        if(title == null || title.isEmpty()) {
            return new ResponseData(false, "채팅방 제목을 입력해주세요.", null);
        }

        Long loginIdx = super.getLoginData(request).getIdx();

        ChattingRoomDto chattingRoomDto;
        if(idx == null) {
            chattingRoomDto = ChattingRoomDto.builder()
                    .code(chatService.createNewCode())
                    .title(title)
                    .register(loginIdx)
                    .build();
        } else {
            chattingRoomDto = chatService.getOne(idx);
            chattingRoomDto.setTitle(title);
            chattingRoomDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
            chattingRoomDto.setModifier(loginIdx);
        }

        // 채팅방 저장
        String result = chatService.saveItem(chattingRoomDto);
        Long roomIdx = chatService.getRoomIdx(result);

        // 채팅방 멤버 저장
        if(deleteMemberList != null && deleteMemberList.length > 0) {
            chatService.deleteChattingMember(deleteMemberList);
        }

        if(memberList != null && memberList.length > 0) {
            chatService.saveChattingMember(roomIdx, memberList, loginIdx);
        }

        return new ResponseData(true, "저장되었습니다.", result);
    }

    // 채팅 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "room_idx") Long roomIdx,
                             @RequestParam(required = false, value = "upload_file") MultipartFile uploadFile,
                             @RequestParam(required = false, value = "message") String message,
                             HttpServletRequest request) {
        if(uploadFile == null && (message == null || message.isEmpty())) {
            return new ResponseData(false, "내용을 입력해주세요.", null);
        }

        Long loginIdx = super.getLoginData(request).getIdx();

        Long fileIdx = null;
        if(uploadFile != null) {
            try {
                fileIdx = fileService.saveItem(uploadFile, loginIdx);
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }

        // 채팅 저장
        Long result = chatService.saveChatting(ChattingDto.builder().chattingRoomIdx(roomIdx).fileIdx(fileIdx).message(message).register(loginIdx).build());

        return new ResponseData(true, "OK", result);
    }

    // 채팅 내역 조회
    @GetMapping("/get_list")
    public ResponseData getList(@RequestParam(required = false, value = "room_idx") Long roomIdx,
                                @RequestParam(required = false, value = "page") Long page,
                                HttpServletRequest request) {
        return new ResponseData(true, "OK", chatService.getChattingList(roomIdx, page));
    }

    // 채팅 조회
    @GetMapping("/get")
    public ResponseData getList(@RequestParam(required = false, value = "idx") Long idx,
                                HttpServletRequest request) {
        return new ResponseData(true, "OK", chatService.getChatting(idx));
    }
}
