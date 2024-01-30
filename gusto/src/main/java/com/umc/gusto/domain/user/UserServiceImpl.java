package com.umc.gusto.domain.user;

import com.umc.gusto.domain.user.entity.Social;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.model.request.SignUpRequest;
import com.umc.gusto.global.auth.JwtService;
import com.umc.gusto.global.auth.model.Tokens;
import com.umc.gusto.global.config.secret.JwtConfig;
import com.umc.gusto.global.util.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final SocialRepository socialRepository;
    private final JwtService jwtService;
    private final RedisService redisService;
    private static final long NICKNAME_EXPIRED_TIME = 1000L * 60 * 15;


    @Value("${default.img.url.profile}")
    private String DEFAULT_IMG;


    @Override
    @Transactional
    public Tokens createUser(String tempToken, MultipartFile multipartFile, SignUpRequest request) {
        // temp token을 사용하여 social 정보 가져오기
        UUID socialUID = UUID.fromString(tempToken);
        Social socialInfo = socialRepository.findByTemporalToken(socialUID).orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));

        redisService.deleteValues(request.getNickname());
        checkNickname(request.getNickname());

        String profileImg = DEFAULT_IMG;
        
        if(multipartFile != null) {
            // profileImg를 이미지 파일로 받았다면
            // TODO:
            //  s3 이미지 업로드 후 생성 된 url로 profileImg url 변경
        } else if(request.getProfileImg() != null) {
            // profileImg를 이미지로 받지 않고, url로 받았다면
            profileImg = request.getProfileImg();
        } // 그 외의 경우, default 이미지로 처리

        System.out.println(profileImg);

        // user 생성
        User user = User.builder()
                .userid(UUID.randomUUID())
                .nickname(request.getNickname())
                .gender(User.Gender.valueOf(request.getGender()))
                .age(User.Age.valueOf(request.getAge()))
                .profileImage(profileImg)
                .build();

        user = userRepository.save(user);
        
        // social entity 정보 갱신
        socialInfo.updateUser(user);
        socialInfo.updateSocialStatus(Social.SocialStatus.CONNECTED);
        socialRepository.save(socialInfo);

        // access-token 및 refresh-token 생성
        Tokens tokens = jwtService.createToken(String.valueOf(user.getUserid()));
        redisService.setValuesWithTimeout(tokens.getRefreshToken(), String.valueOf(user.getUserid()), JwtConfig.REFRESH_TOKEN_VALID_TIME);

        return tokens;
    }

    @Override
    public void checkNickname(String nickname) {
        // redis 내 검색
        redisService.getValues(nickname).orElseThrow(() -> new RuntimeException("이미 사용중인 닉네임입니다."));

        // DB 내 검색
        if(userRepository.countUsersByNicknameAndMemberStatusIs(nickname, User.MemberStatus.ACTIVE) > 0) {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }
    }

    @Override
    public void confirmNickname(String nickname) {
        checkNickname(nickname);
        redisService.setValuesWithTimeout(nickname, null, NICKNAME_EXPIRED_TIME);
    }


}
