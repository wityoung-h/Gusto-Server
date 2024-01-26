package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.apiPayload.ApiResponse;
import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.repository.MyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyCategoryCommandServiceImpl implements MyCategoryCommandService{

    private final MyCategoryRepository myCategoryRepository;

    public List<MyCategoryResponse.MyCategoryDTO> getAllMyCategory() {
        List<MyCategory> myCategoryList = myCategoryRepository.findAll();

        return myCategoryList.stream()
                .map(myCategory -> MyCategoryResponse.MyCategoryDTO.builder()
                        .myCategoryId(myCategory.getMyCategoryId())
                        .myCategoryName(myCategory.getMyCategoryName())
                        .myCategoryIcon(myCategory.getMyCategoryIcon())
                        .publishCategory(myCategory.getPublishCategory())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public MyCategory createMyCategory(MyCategoryRequest.createMyCategoryDTO createMyCategoryDTO) {
        MyCategory myCategory = MyCategory.builder()
                .myCategoryName(createMyCategoryDTO.getMyCategoryName())
                .myCategoryIcon(createMyCategoryDTO.getMyCategoryIcon())
                .myCategoryScript(createMyCategoryDTO.getMyCategoryScript())
                .publishCategory(createMyCategoryDTO.getPublishCategory())
                .build();
        return myCategoryRepository.save(myCategory);
    }



}