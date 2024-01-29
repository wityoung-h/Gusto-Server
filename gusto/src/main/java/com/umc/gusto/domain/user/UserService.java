package com.umc.gusto.domain.user;

import com.umc.gusto.domain.user.model.request.SignUpRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    
    // 회원 가입 시 사용
    void createUser(String tempToken, MultipartFile multipartFile, SignUpRequest signUpRequest);
}
