package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.domain.myCategory.service.MyCategoryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myCategories")
public class MyCategoryController {
    private final MyCategoryCommandService myCategoryCommandService;

    @GetMapping("/")
    p

}
