package com.hppystay.hotelreservation.common.util;

import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileService {

    // 이미지 저장
    public String saveImage(
            String rootDir,
            String relativeDir,
            MultipartFile image
    ) {
        String imgDir = rootDir + relativeDir;
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path imgPath = Path.of(imgDir + imgName);

        try {
            Files.createDirectories(Path.of(imgDir));
            image.transferTo(imgPath);
        } catch (IOException e) {
            throw new GlobalException(GlobalErrorCode.PROFILE_UPLOAD_FAILED);
        }
        return relativeDir + imgName;
    }

    // 이미지 삭제
    public void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Path.of(filePath));
        } catch (IOException e) {
            throw new GlobalException(GlobalErrorCode.PROFILE_DELETE_FAILED);
        }
    }
}
