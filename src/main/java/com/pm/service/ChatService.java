package com.pm.service;

import com.pm.dto.ChattingDto;
import com.pm.dto.ChattingMemberDto;
import com.pm.dto.ChattingRoomDto;
import com.pm.entity.Chatting;
import com.pm.entity.ChattingRoom;
import com.pm.repository.ChattingMemberRepository;
import com.pm.repository.ChattingRepository;
import com.pm.repository.ChattingRoomRepository;
import com.pm.util.Paging;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 * Chat Service
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-13
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-13          HTH             최초 등록
 **/
@Service
public class ChatService {
    private final ChattingRepository chattingRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChattingMemberRepository chattingMemberRepository;
    public ChatService(ChattingRepository chattingRepository, ChattingRoomRepository chattingRoomRepository, ChattingMemberRepository chattingMemberRepository) {
        this.chattingRepository = chattingRepository;
        this.chattingRoomRepository = chattingRoomRepository;
        this.chattingMemberRepository = chattingMemberRepository;
    }
    
    // 목록 조회
    @Transactional
    public List<ChattingRoomDto> getList(Long memberIdx) {
        List<ChattingRoomDto> resultList = new ArrayList<>();
        chattingRoomRepository.findByMemberIdx(memberIdx).forEach(chattingRoom -> {
            resultList.add(chattingRoom.toDto());
        });

        return resultList;
    }

    // 고유번호 조회
    @Transactional
    public Long getRoomIdx(String code) {
        return chattingRoomRepository.findByCode(code).getIdx();
    }
    
    // 단건 조회
    @Transactional
    public ChattingRoomDto getOne(Long idx) {
        return chattingRoomRepository.findById(idx).map(ChattingRoom::toDto).orElse(null);
    }
    
    // 생성/수정
    @Transactional
    public String saveItem(ChattingRoomDto chattingRoomDto) {
        return chattingRoomRepository.save(chattingRoomDto.toEntity()).getCode();
    }
    
    // 삭제
    @Transactional
    public void deleteItem(Long idx) {
        chattingRoomRepository.deleteById(idx);
    }
    
    // 채팅 조회
    @Transactional
    public List<ChattingDto> getChattingList(Long chattingRoomIdx, Long page) {
        Long offset = (page - 1) * Paging.getPer();

        List<ChattingDto> resultList = new ArrayList<>();
        chattingRepository.findChatting(chattingRoomIdx, Paging.getPer(), offset).forEach(chatting -> {
            resultList.add(chatting.toDto());
        });

        return resultList;
    }

    // 채팅 조회(단건)
    @Transactional
    public ChattingDto getChatting(Long idx) {
        return chattingRepository.findById(idx).map(Chatting::toDto).orElse(null);
    }
    
    // 채팅 저장
    @Transactional
    public Long saveChatting(ChattingDto chattingDto) {
        return chattingRepository.save(chattingDto.toEntity()).getIdx();
    }
    
    // 멤버 조회
    @Transactional
    public List<ChattingMemberDto> getChattingMemberList(Long chattingRoomIdx) {
        List<ChattingMemberDto> resultList = new ArrayList<>();
        chattingMemberRepository.findByChattingRoomIdx(chattingRoomIdx).forEach(chattingMember -> {
            resultList.add(chattingMember.toDto());
        });

        return resultList;
    }
    
    // 멤버 저장
    @Transactional
    public void saveChattingMember(Long chattingRoomIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(memberIdx -> {
            chattingMemberRepository.save(ChattingMemberDto.builder().chattingRoomIdx(chattingRoomIdx).memberIdx(memberIdx).register(register).build().toEntity());
        });
    }
    
    // 멤버 삭제
    @Transactional
    public void deleteChattingMember(Long[] idxArray) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(chattingMemberRepository::deleteById);
    }

    // 코드 생성
    public String createNewCode() {
        String code = getRandomCode(30);

        // 중복 코드인 경우 재생성
        while(chattingRoomRepository.existsByCode(code)) {
            code = getRandomCode(30);
        }

        return code;
    }

    private String getRandomCode(int length) {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        String ableCharacter = "ABCDEFGHIJKLMNOPQRLSTUVWXYZ1234567890";
        int ableCharacterLength = ableCharacter.length();

        for(int i = 0; i < length; i++) {
            codeBuilder.append(ableCharacter.charAt(random.nextInt(ableCharacterLength)));
        }

        return codeBuilder.toString();
    }
}
