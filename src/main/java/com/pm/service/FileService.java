package com.pm.service;

import com.pm.dto.FileDto;
import com.pm.entity.File;
import com.pm.repository.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

/**
 * 
 * File Service
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
@Service
public class FileService {
    @Value("${file.path}")
    private String uploadPath;
    private final FileRepository fileRepository;
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    // 단건 조회
    @Transactional
    public FileDto getOne(Long idx) {
        Optional<File> wrapper = fileRepository.findById(idx);
        if(wrapper.isEmpty()) {
            return null;
        }
        
        File file = wrapper.get();

        FileDto fileDto = file.toDto();
        fileDto.setSavePath(uploadPath + file.getSavePath());

        return fileDto;
    }

    // 업로드
    @Transactional
    public Long saveItem(MultipartFile uploadFile) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(UUID.randomUUID().toString().replaceAll("-", ""));

        // 디렉토리 없으면 생성
        String savePath = builder.toString();
        java.io.File uploadPathFolder = new java.io.File(uploadPath, builder.toString());

        if(!uploadPathFolder.exists()) {
            if(uploadPathFolder.mkdirs()) return null;
        }

        builder.append("/");
        builder.append(uploadFile.getOriginalFilename());
        savePath = builder.toString();

        Path path = Paths.get(uploadPath + savePath);
        uploadFile.transferTo(path);

        // DB INSERT
        FileDto fileDto = FileDto.builder()
                .savePath(savePath)
                .originName(uploadFile.getOriginalFilename())
                .build();

        return fileRepository.save(fileDto.toEntity()).getIdx();
    }

    // 파일 미리보기
    @Transactional
    public byte[] getByte(Long idx) throws IOException {
        Optional<File> wrapper = fileRepository.findById(idx);
        if(wrapper.isEmpty()) return null;

        File file = wrapper.get();
        return Files.readAllBytes(new java.io.File(uploadPath, file.getSavePath()).toPath());
    }
}
