package com.umc.gusto.domain.user;

import com.umc.gusto.domain.user.model.request.SignUpRequest;
import com.umc.gusto.global.auth.model.Tokens;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    
    // 회원 가입
    Tokens createUser(String tempToken, MultipartFile multipartFile, SignUpRequest signUpRequest);

    // 닉네임 중복 체크
    void checkNickname(String nickname);

    // 닉네임 사용 확정 - 회원 가입 시
    void confirmNickname(String nickname);

    // 닉네임 랜덤 생성
    String generateRandomNickname();
}
