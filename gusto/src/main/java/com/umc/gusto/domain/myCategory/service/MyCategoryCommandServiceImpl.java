package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.entity.Pin;
import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.repository.MyCategoryRepository;
import com.umc.gusto.domain.myCategory.repository.PinRepository;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.repository.ReviewRepository;;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.entity.Town;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.store.repository.TownRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.repository.UserRepository;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.PublishStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyCategoryCommandServiceImpl implements MyCategoryCommandService{

    private final MyCategoryRepository myCategoryRepository;
    private final PinRepository pinRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final TownRepository townRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public List<MyCategoryResponse.MyCategory> getAllMyCategory(String nickname) {
        User user = userRepository.findByNickname(nickname);

        List<MyCategory> myCategoryList = myCategoryRepository.findFilteredNicknames(
                BaseEntity.Status.ACTIVE, user, PublishStatus.PUBLIC
        );

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

    @Transactional
    public List<MyCategoryResponse.MyCategory> getAllMyCategoryWithLocation(User user,String townName) {
        List<MyCategory>myCategoryList = myCategoryRepository.findByStatus(BaseEntity.Status.ACTIVE);      // status가 ACTIVE인 카테고리 조회
        Optional<Town> town = townRepository.findByTownName(townName);
        List<Store> storesList = storeRepository.findByTown(town.orElse(null));                                         // 특정 townName인 storesList

        return myCategoryList.stream()
                .map(myCategory -> {
                    List<Pin> pinList = pinRepository.findAllByMyCategoryOrderByPinIdDesc(myCategory);     // 먼저 카테고리로 구분
                    List<Store> storesWithPins = pinList.stream()
                            .map(Pin::getStore)
                            .filter(storesList::contains)                                                  // storesList에 포함된 store만 필터링!
                            .toList();

                    return MyCategoryResponse.MyCategory.builder()
                            .myCategoryId(myCategory.getMyCategoryId())
                            .myCategoryName(myCategory.getMyCategoryName())
                            .myCategoryIcon(myCategory.getMyCategoryIcon())
                            .publishCategory(myCategory.getPublishCategory())
                            .pinCnt(storesWithPins.size())            // pin 개수 받아오기로 변경
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MyCategoryResponse.PinByMyCategory> getAllPinByMyCategory(String nickname, Long myCategoryId) {
        User user = userRepository.findByNickname(nickname);
        Optional<MyCategory> existingMyCategory = myCategoryRepository.findByMyCategoryIdAndUser(myCategoryId, user);
        // 카테고리 별 가게 목록이 비어있으면 pinList도 비어 있음
        List<Pin> pinList = existingMyCategory.map(pinRepository::findByMyCategoryOrderByPinIdDesc)
                .orElse(Collections.emptyList());

        return pinList.stream()
                .map(pin -> {
                    Optional<Review> topReviewOptional = reviewRepository.findTopByStoreOrderByLikedDesc(pin.getStore());       // 가장 좋아요가 많은 review
                    String reviewImg = topReviewOptional.map(Review::getImg1).orElse(null);                               // 가장 좋아요가 많은 review 이미지
                    Integer reviewCnt = reviewRepository.countByStoreAndUser(pin.getStore(), user);                             // 내가 작성한 리뷰의 개수 == 방문 횟수

                    return  MyCategoryResponse.PinByMyCategory.builder()
                                .pinId(pin.getPinId())
                                .storeName(pin.getStore().getStoreName())
                                .address(pin.getStore().getAddress())
                                .reviewImg(reviewImg)
                                .reviewCnt(reviewCnt)
                                .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MyCategoryResponse.PinByMyCategory> getAllPinByMyCategoryWithLocation(User user,Long myCategoryId, String townName) {
        Optional<MyCategory> existingMyCategory = myCategoryRepository.findByMyCategoryId(myCategoryId);
        Optional<Town> town = townRepository.findByTownName(townName);
        List<Store> storesList = storeRepository.findByTown(town.orElse(null));
        List<Pin> pinList = existingMyCategory.map(pinRepository::findByMyCategoryOrderByPinIdDesc)
                .orElse(Collections.emptyList());

        return pinList.stream()
                .filter(pin -> storesList.contains(pin.getStore()))             // townName을 기준으로 보일 수 있는 store가 포함된 pin만 보이기(핀은 화살표 오른쯕을 기준으로 형성)
                .map(pin -> {
                    Optional<Review> topReviewOptional = reviewRepository.findTopByStoreOrderByLikedDesc(pin.getStore());       // 가장 좋아요가 많은 review
                    String reviewImg = topReviewOptional.map(Review::getImg1).orElse(null);                               // 가장 좋아요가 많은 review 이미지
                    Integer reviewCnt = reviewRepository.countByStore(pin.getStore());                             // 내가 작성한 리뷰의 개수 == 방문 횟수

                    return  MyCategoryResponse.PinByMyCategory.builder()
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
    @Transactional
    public void createMyCategory(User user, MyCategoryRequest.createMyCategory createMyCategory) {
        // 중복 이름 체크
        myCategoryRepository.findByMyCategoryNameAndUser(createMyCategory.getMyCategoryName(), user)
                .ifPresent(existingCategory -> {
                    if (existingCategory.getStatus() == BaseEntity.Status.INACTIVE) {
                        existingCategory.setStatus(BaseEntity.Status.ACTIVE);
                        myCategoryRepository.save(existingCategory);
                    } else {
                        throw new RuntimeException("MyCategory is already present");
                    }
                });
//                .orElse(() -> {
//                    // 중복된 이름이 없으면 새로운 MyCategory 생성
//                    MyCategory myCategory = MyCategory.builder()
//                            .myCategoryName(createMyCategory.getMyCategoryName())
//                            .myCategoryIcon(createMyCategory.getMyCategoryIcon())
//                            .myCategoryScript(createMyCategory.getMyCategoryScript())
//                            .publishCategory(createMyCategory.getPublishCategory())
//                            .user(user)
//                            .build();
//
//                    myCategoryRepository.save(myCategory);
//                });
    }





    @Transactional
    public void modifyMyCategory(User user, Long myCategoryId, MyCategoryRequest.updateMyCategory request) {
        MyCategory existingMyCategory = myCategoryRepository.findById(myCategoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + myCategoryId));
            // status가 INACTIVE일 때 수정하는 경우 -> 프론트 상 그럴 경우 없어 보여 주석 처리

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

    @Transactional
    public void deleteMyCategories(User user, List<Long> myCategoryIds) {
        for (Long myCategoryId : myCategoryIds) {
            MyCategory existingMyCategory = myCategoryRepository.findById(myCategoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + myCategoryId));

            existingMyCategory.setStatus(BaseEntity.Status.INACTIVE);

            myCategoryRepository.save(existingMyCategory);
        }
    }

}