package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.entity.Pin;
import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.repository.MyCategoryRepository;
import com.umc.gusto.domain.myCategory.repository.PinRepository;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.repository.ReviewRepository;;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.repository.UserRepository;
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
    private final PinRepository pinRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public List<MyCategoryResponse.MyCategoryDTO> getAllMyCategory(String nickname) {
        User user = userRepository.findByNickname(nickname);

        List<MyCategory> myCategoryList = myCategoryRepository.findByStatusAndUser(BaseEntity.Status.ACTIVE, user);      // status가 ACTIVE인 카테고리 조회

        return myCategoryList.stream()
                .map(myCategory -> MyCategoryResponse.MyCategoryDTO.builder()
                        .myCategoryId(myCategory.getMyCategoryId())
                        .myCategoryName(myCategory.getMyCategoryName())
                        .myCategoryIcon(myCategory.getMyCategoryIcon())
                        .publishCategory(myCategory.getPublishCategory())
                        .pinCnt(myCategory.getPinList().size())            // pin 개수 받아오기로 변경
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public List<MyCategoryResponse.PinByMyCategoryDTO> getAllPinByMyCategory(String nickname, Long myCategoryId, String dong) {
        User user = userRepository.findByNickname(nickname);
        MyCategory existingMyCategory = myCategoryRepository.findByMyCategoryIdAndUser(myCategoryId, user);
        List<Pin> pinList = pinRepository.findByMyCategoryOrderByPinIdDesc(existingMyCategory);

        return pinList.stream()
                .map(pin -> {
                    Optional<Review> topReviewOptional = reviewRepository.findTopByStoreOrderByLikedDesc(pin.getStore());       // 가장 좋아요가 많은 review
                    String reviewImg = topReviewOptional.map(Review::getImg1).orElse(null);                               // 가장 좋아요가 많은 review 이미지
                    Integer reviewCnt = reviewRepository.countByStoreAndUser(pin.getStore(), user);                             // 내가 작성한 리뷰의 개수 == 방문 횟수

                    return  MyCategoryResponse.PinByMyCategoryDTO.builder()
                                .pinId(pin.getPinId())
                                .storeName(pin.getStore().getStoreName())
                                .address(pin.getStore().getAddress())
                                .reviewImg(reviewImg)
                                .reviewCnt(reviewCnt)
                                .build();
                })
                .collect(Collectors.toList());
    }

    // 동일 user가 작성한 카테고리 중 겹치는 NAME이 있다면 추가 X, 겹치는 name인데 status가 inactive면 active로 변경 => 토큰을 통해 nickname 가져올 것
    @Override
    public void createMyCategory(MyCategoryRequest.createMyCategoryDTO createMyCategoryDTO) {
        // 중복 이름 체크
        MyCategory myCategoryOptional = myCategoryRepository.findByMyCategoryName(createMyCategoryDTO.getMyCategoryName())
                .orElseThrow(() -> new RuntimeException("MyCategory with the same name does not exist"));


        if (myCategoryOptional.getStatus() == BaseEntity.Status.INACTIVE) {
            myCategoryOptional.setStatus(BaseEntity.Status.ACTIVE);
            myCategoryRepository.save(myCategoryOptional);
        } else {
            throw new RuntimeException("MyCategory with the same name already exists and is in ACTIVE");
        }

        MyCategory myCategory = MyCategory.builder()
                .myCategoryName(createMyCategoryDTO.getMyCategoryName())
                .myCategoryIcon(createMyCategoryDTO.getMyCategoryIcon())
                .myCategoryScript(createMyCategoryDTO.getMyCategoryScript())
                .publishCategory(createMyCategoryDTO.getPublishCategory())
                .build();

        myCategoryRepository.save(myCategory);
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

    public void deleteMyCategories(List<Long> myCategoryIds) {
        for (Long myCategoryId : myCategoryIds) {
            MyCategory existingMyCategory = myCategoryRepository.findById(myCategoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + myCategoryId));

            existingMyCategory.setStatus(BaseEntity.Status.INACTIVE);

            myCategoryRepository.save(existingMyCategory);
        }
    }

}