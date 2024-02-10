package com.umc.gusto.domain.user.service;

import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.model.request.SignUpRequest;
import com.umc.gusto.domain.user.model.response.FollowResponse;
import com.umc.gusto.domain.user.model.response.ProfileRes;
import com.umc.gusto.global.auth.model.Tokens;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    
    // 회원 가입
    Tokens createUser(String tempToken, MultipartFile multipartFile, SignUpRequest signUpRequest);

    // 닉네임 중복 체크
    void checkNickname(String nickname);

    // 닉네임 사용 확정 - 회원 가입 시
    void confirmNickname(String nickname);

    // 닉네임 랜덤 생성
    String generateRandomNickname();

    // 먹스또 프로필 조회
    ProfileRes getProfile(String nickname);

    // 팔로우
    void followUser(User user, String nickname);

    // 언팔로우
    void unfollowUser(User user, String nickname);

    // 팔로우 목록
    List<FollowResponse> getFollowList(User user, Long followId);

    // 팔로워 목록
    List<FollowResponse> getFollwerList(User user, Long followId);
}
