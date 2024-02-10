package com.umc.gusto.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("image")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service imageService;

    @PostMapping("/list")
    public ResponseEntity<List<String>> uploadImages(
            @RequestPart(value = "file", required = false) List<MultipartFile> images) {
        List<String> fileUrls = imageService.uploadImages(images);
        return ResponseEntity.ok().body(fileUrls);
    }

    @PostMapping()
    public ResponseEntity<String> uploadImage(
            @RequestPart(value = "file", required = false) MultipartFile image){
        String url = imageService.uploadImage(image);
        return ResponseEntity.ok().body(url);
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable("fileName") String fileName) { // 파일 삭제 시 .png까지 포함하여 fileName으로 작성
        imageService.deleteImage(fileName);
        return ResponseEntity.ok().build();
    }

}
