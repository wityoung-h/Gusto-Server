package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
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

    @Override
    public List<MyCategoryResponse.MyCategory> getAllMyCategory() {
        List<MyCategory> myCategoryList = myCategoryRepository.findAll();

        return myCategoryList.stream()
                .map(myCategory -> MyCategoryResponse.MyCategory.builder()
                        .myCategoryId(myCategory.getMyCategoryId())
                        .myCategoryName(myCategory.getMyCategoryName())
                        .myCategoryIcon(myCategory.getMyCategoryIcon())
                        .build())
                .collect(Collectors.toList());
    }
}
