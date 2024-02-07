package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.entity.Pin;
import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.repository.MyCategoryRepository;
import com.umc.gusto.domain.myCategory.repository.PinRepository;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyCategoryServiceImpl implements MyCategoryService {

    private final MyCategoryRepository myCategoryRepository;
    private final PinRepository pinRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<MyCategoryResponse.MyCategory> getAllMyCategory(String nickname) {

        List<MyCategory> myCategoryList = myCategoryRepository.findByUserNickname(nickname);

        return myCategoryList.stream()
                .map(myCategory -> MyCategoryResponse.MyCategory.builder()
                        .myCategoryId(myCategory.getMyCategoryId())
                        .myCategoryName(myCategory.getMyCategoryName())
                        .myCategoryIcon(myCategory.getMyCategoryIcon())
                        .publishCategory(myCategory.getPublishCategory())
                        .pinCnt(myCategory.getPinList().size())            // pin 개수 받아오기로 변경
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MyCategoryResponse.MyCategory> getAllMyCategoryWithLocation(User user, String townName) {
        List<MyCategory> myCategoryList = myCategoryRepository.findByStatusAndPublishCategoryAndUser(user);                                      // 특정 townName인 storesList

        return myCategoryList.stream()
                .map(myCategory -> {
                    List<Pin> pinList = pinRepository.findAllByUserAndMyCategoryOrderByPinIdDesc(myCategory, townName);     // 먼저 카테고리로 구분

                    return MyCategoryResponse.MyCategory.builder()
                            .myCategoryId(myCategory.getMyCategoryId())
                            .myCategoryName(myCategory.getMyCategoryName())
                            .myCategoryIcon(myCategory.getMyCategoryIcon())
                            .publishCategory(myCategory.getPublishCategory())
                            .pinCnt(pinList.size())            // pin 개수 받아오기로 변경
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MyCategoryResponse.PinByMyCategory> getAllPinByMyCategory(String nickname, Long myCategoryId) {
        Optional<MyCategory> existingMyCategory = myCategoryRepository.findById(myCategoryId);
        // 카테고리 별 가게 목록이 비어있으면 pinList도 비어 있음
        List<Pin> pinList = existingMyCategory.map(pinRepository::findByMyCategoryOrderByPinIdDesc)
                .orElse(Collections.emptyList());

        return pinList.stream()
                .map(pin -> {
                    Store store = pin.getStore();
                    Optional<Review> topReviewOptional = reviewRepository.findFirstByStoreOrderByLikedDesc(store);
                    String reviewImg = topReviewOptional.map(Review::getImg1).orElse(null);
                    Integer reviewCnt = reviewRepository.countByStoreAndUserNickname(store, nickname);

                    return  MyCategoryResponse.PinByMyCategory.builder()
                            .pinId(pin.getPinId())
                            .storeId(store.getStoreId())
                            .storeName(store.getStoreName())
                            .address(store.getAddress())
                            .reviewImg(reviewImg)
                            .reviewCnt(reviewCnt)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MyCategoryResponse.PinByMyCategory> getAllPinByMyCategoryWithLocation(User user, Long myCategoryId, String townName) {
        List<Pin> pinList = myCategoryRepository.findPinsByMyCategoryIdAndTownName(myCategoryId, townName);

        return pinList.stream()                                     // townName을 기준으로 보일 수 있는 store가 포함된 pin만 보이기
                .map(pin -> {
                    Optional<Review> topReviewOptional = reviewRepository.findFirstByStoreOrderByLikedDesc(pin.getStore());       // 가장 좋아요가 많은 review
                    String reviewImg = topReviewOptional.map(Review::getImg1).orElse(null);                               // 가장 좋아요가 많은 review 이미지
                    Integer reviewCnt = reviewRepository.countByStoreAndUserNickname(pin.getStore(), user.getNickname());                        // 내가 작성한 리뷰의 개수 == 방문 횟수

                    return  MyCategoryResponse.PinByMyCategory.builder()
                            .pinId(pin.getPinId())
                            .storeId(pin.getStore().getStoreId())
                            .storeName(pin.getStore().getStoreName())
                            .address(pin.getStore().getAddress())
                            .reviewImg(reviewImg)
                            .reviewCnt(reviewCnt)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void createMyCategory(User user, MyCategoryRequest.createMyCategory createMyCategory) {
        // 중복 이름 체크
        myCategoryRepository.findByMyCategoryNameAndUser(createMyCategory.getMyCategoryName(), user)
                .ifPresent(existingCategory -> {
                    throw new NotFoundException(Code.MYCATEGORY_DUPLICATE_NAME);
                });

        // 중복된 이름이 없으면 새로운 MyCategory 생성
        MyCategory myCategory = MyCategory.builder()
                .myCategoryName(createMyCategory.getMyCategoryName())
                .myCategoryIcon(createMyCategory.getMyCategoryIcon())
                .myCategoryScript(createMyCategory.getMyCategoryScript())
                .publishCategory(createMyCategory.getPublishCategory())
                .user(user)
                .build();

        myCategoryRepository.save(myCategory);
    }


    @Transactional
    public void modifyMyCategory(User user, Long myCategoryId, MyCategoryRequest.updateMyCategory updateMyCategory) {
        MyCategory existingMyCategory = myCategoryRepository.findByUserAndMyCategoryId(user,myCategoryId)
                .orElseThrow(() -> new NotFoundException(Code.MYCATEGORY_NOT_FOUND));

        // 중복 이름 체크
        if (updateMyCategory.getMyCategoryName() != null && updateMyCategory.getMyCategoryName().equals(existingMyCategory.getMyCategoryName())) {
            myCategoryRepository.findByMyCategoryNameAndUser(updateMyCategory.getMyCategoryName(), user)
                    .ifPresent(existingCategory -> {
                throw new NotFoundException(Code.MYCATEGORY_DUPLICATE_NAME);
            });
        }

            // 변경하려는 필드만 업데이트
            if (updateMyCategory.getMyCategoryName() != null) {
                existingMyCategory.updateMyCategoryName(updateMyCategory.getMyCategoryName());
            }

            if (updateMyCategory.getMyCategoryIcon() != null) {
                existingMyCategory.updateMyCategoryIcon(updateMyCategory.getMyCategoryIcon());
            }

            if (updateMyCategory.getMyCategoryScript() != null) {
                existingMyCategory.updateMyCategoryScript(updateMyCategory.getMyCategoryScript());
            }

            if (updateMyCategory.getPublishCategory() != null) {
                existingMyCategory.updatePublishCategory(updateMyCategory.getPublishCategory());
            }

            myCategoryRepository.save(existingMyCategory);
    }

    @Transactional
    public void deleteMyCategories(User user, List<Long> myCategoryIds) {
        for (Long myCategoryId : myCategoryIds) {
            MyCategory existingMyCategory = myCategoryRepository.findByUserAndMyCategoryId(user, myCategoryId)
                    .orElseThrow(() -> new NotFoundException(Code.MYCATEGORY_NOT_FOUND));

            existingMyCategory.updateStatus(BaseEntity.Status.INACTIVE);

            myCategoryRepository.save(existingMyCategory);
        }
    }

}