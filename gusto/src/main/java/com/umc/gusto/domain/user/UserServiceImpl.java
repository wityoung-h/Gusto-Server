package com.umc.gusto.domain.user;

import com.umc.gusto.domain.user.model.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public void createUser(String tempToken, MultipartFile multipartFile, SignUpRequest request) {
        return;
    }
}
