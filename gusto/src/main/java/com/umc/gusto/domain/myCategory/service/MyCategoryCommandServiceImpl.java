package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.repository.MyCategoryRepository;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.global.common.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyCategoryCommandServiceImpl implements MyCategoryCommandService{

    private final MyCategoryRepository myCategoryRepository;
    private final StoreRepository storeRepository;

    public List<MyCategoryResponse.MyCategoryDTO> getAllMyCategory() {
        List<MyCategory> myCategoryList = myCategoryRepository.findByStatus(BaseEntity.Status.ACTIVE);      // status가 ACTIVE인 카테고리 조회

        return myCategoryList.stream()
                .map(myCategory -> MyCategoryResponse.MyCategoryDTO.builder()
                        .myCategoryId(myCategory.getMyCategoryId())
                        .myCategoryName(myCategory.getMyCategoryName())
                        .myCategoryIcon(myCategory.getMyCategoryIcon())
                        .publishCategory(myCategory.getPublishCategory())
                        .myStoresCnt(myCategory.getStoreList().size())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<MyCategoryResponse.MyStoreByMyCategoryDTO> getAllMyStoreByMyCategory(Long myCategoryId) {
        MyCategory existingMyCategory = myCategoryRepository.findById(myCategoryId)
                .orElseThrow(() -> new RuntimeException(""));
        List<Store> myStoreList;
        myStoreList = storeRepository.findByMyCategory(existingMyCategory);

        return myStoreList.stream()
                .map(store -> MyCategoryResponse.MyStoreByMyCategoryDTO.builder()
                        .storeId(store.getStoreId())
                        .storeName(store.getStoreName())
                        .address(store.getAddress())
                        .build())
                .collect(Collectors.toList());
    }

    // 겹치는 NAME이 있다면 추가 X, 겹치는 name인데 status가 inactive면 active로 변경
    @Override
    public void createMyCategory(MyCategoryRequest.createMyCategoryDTO createMyCategoryDTO) {

        // 중복 이름 체크
        Optional<MyCategory> myCategoryOptional = myCategoryRepository.findByMyCategoryName(createMyCategoryDTO.getMyCategoryName());

        if (myCategoryOptional.isPresent()) {
            MyCategory existing = myCategoryOptional.get();

            if (existing.getStatus() == BaseEntity.Status.INACTIVE) {
                existing.setStatus(BaseEntity.Status.ACTIVE);
                myCategoryRepository.save(existing);
            } else {
                throw new RuntimeException("MyCategory with the same name already exists and is in ACTIVE");
            }
        } else {
            MyCategory myCategory = MyCategory.builder()
                    .myCategoryName(createMyCategoryDTO.getMyCategoryName())
                    .myCategoryIcon(createMyCategoryDTO.getMyCategoryIcon())
                    .myCategoryScript(createMyCategoryDTO.getMyCategoryScript())
                    .publishCategory(createMyCategoryDTO.getPublishCategory())
                    .build();

            myCategoryRepository.save(myCategory);
        }


    }

    public void modifyMyCategory(Long myCategoryId, MyCategoryRequest.updateMyCategoryDTO request) {
        MyCategory existingMyCategory = myCategoryRepository.findById(myCategoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + myCategoryId));
            // status가 INACTIVE일 때 수정하는 경우 -> 프론트 상 그럴 경우 없어 보여 주석 처리
//        if (existingMyCategory.getStatus() != BaseEntity.Status.INACTIVE) {
            // 변경하려는 필드만 업데이트
            if (request.getMyCategoryName() != null) {
                existingMyCategory.setMyCategoryName(request.getMyCategoryName());
            }

            if (request.getMyCategoryIcon() != null) {
                existingMyCategory.setMyCategoryIcon(request.getMyCategoryIcon());
            }

            if (request.getMyCategoryScript() != null) {
                existingMyCategory.setMyCategoryScript(request.getMyCategoryScript());
            }

            if (request.getPublishCategory() != null) {
                existingMyCategory.setPublishCategory(request.getPublishCategory());
            }

            myCategoryRepository.save(existingMyCategory);
//        } else {
//            throw new RuntimeException("dont' update delete myCategory");
//        }
    }

    @Override
    public void deleteMyCategory(Long myCategoryId, MyCategoryRequest.deleteMyCategoryDTO request) {
        MyCategory existingMyCategory = myCategoryRepository.findById(myCategoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + myCategoryId));

        existingMyCategory.setStatus(BaseEntity.Status.INACTIVE);

        myCategoryRepository.save(existingMyCategory);

    }

}