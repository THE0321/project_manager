package com.pm.api;

import com.pm.dto.DriveDto;
import com.pm.dto.FileDto;
import com.pm.service.DriveService;
import com.pm.service.FileService;
import com.pm.util.Controller;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * Upload REST Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/upload")
public class UploadRestController extends Controller {
    private final FileService fileService;
    private final DriveService driveService;
    public UploadRestController(FileService fileService, DriveService driveService) {
        this.fileService = fileService;
        this.driveService = driveService;
    }

    // 파일 업로드(드라이브)
    @PostMapping("/drive")
    public ResponseData driveUpload(@RequestParam("file") MultipartFile uploadFile,
                                    HttpServletRequest request) throws IOException {
        Long loginIdx = super.getLoginData(request).getIdx();
        Long projectIdx = super.getProjectData(request);

        Long fileIdx = fileService.saveItem(uploadFile, loginIdx);

        return new ResponseData(true, "저장되었습니다.", driveService.saveItem(DriveDto.builder().projectIdx(projectIdx).fileIdx(fileIdx).build()));
    }

    // 파일 업로드
    @PostMapping(value = {"/", ""})
    public ResponseData upload(@RequestParam("file") MultipartFile uploadFile,
                               HttpServletRequest request) throws IOException {
        Long loginIdx = super.getLoginData(request).getIdx();

        return new ResponseData(true, "저장되었습니다.", fileService.saveItem(uploadFile, loginIdx));
    }

    // 파일 다운로드
    @GetMapping("/download/{idx}")
    public void download(HttpServletResponse response, @PathVariable("idx") Long idx) {
        FileDto fileDto =  fileService.getOne(idx);
        String filePath = fileDto.getSavePath();
        String fileName = fileDto.getOriginName();
        String contentType = fileDto.getExtension();

        File file = new File(filePath);
        long fileLength = file.length();

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");

        try(
                FileInputStream fis = new FileInputStream(filePath);
                OutputStream out = response.getOutputStream();
        ){
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while((readCount = fis.read(buffer)) != -1){
                out.write(buffer, 0, readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
    }

    // 이미지 미리보기
    @GetMapping("/view/image/{idx}")
    public @ResponseBody byte[] viewImage(@PathVariable("idx") Long idx) throws IOException {
        return fileService.getByte(idx);
    }
}
