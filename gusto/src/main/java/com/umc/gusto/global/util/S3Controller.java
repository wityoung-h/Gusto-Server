package com.umc.gusto.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("image")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service imageService;

    @PostMapping()
    public ResponseEntity<List<String>> uploadImages(
            @RequestPart(value = "file", required = false) List<MultipartFile> images) {
        List<String> fileUrls = imageService.uploadImages(images);
        return ResponseEntity.ok().build();
    }


}
