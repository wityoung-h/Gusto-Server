package com.umc.gusto.domain.user;

import com.umc.gusto.domain.user.model.request.SignUpRequest;
import com.umc.gusto.global.auth.model.Tokens;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    
    // 회원 가입 시 사용
    Tokens createUser(String tempToken, MultipartFile multipartFile, SignUpRequest signUpRequest);
}
