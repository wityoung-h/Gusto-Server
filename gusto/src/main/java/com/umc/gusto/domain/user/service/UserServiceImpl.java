package com.umc.gusto.domain.user.service;

import com.umc.gusto.domain.user.entity.Social;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.model.NicknameBucket;
import com.umc.gusto.domain.user.model.request.PublishingInfoRequest;
import com.umc.gusto.domain.user.model.request.SignUpRequest;
import com.umc.gusto.domain.user.model.request.UpdateProfileRequest;
import com.umc.gusto.domain.user.model.response.ProfileResponse;
import com.umc.gusto.domain.user.model.response.PublishingInfoResponse;
import com.umc.gusto.domain.user.repository.FollowRepository;
import com.umc.gusto.domain.user.repository.SocialRepository;
import com.umc.gusto.domain.user.repository.UserRepository;
import com.umc.gusto.global.auth.JwtService;
import com.umc.gusto.global.auth.model.Tokens;
import com.umc.gusto.global.common.PublishStatus;
import com.umc.gusto.global.config.secret.JwtConfig;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import com.umc.gusto.global.util.RedisService;
import com.umc.gusto.global.util.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final SocialRepository socialRepository;
    private final JwtService jwtService;
    private final RedisService redisService;
    private final FollowRepository followRepository;
    private final S3Service s3Service;

    private static final long NICKNAME_EXPIRED_TIME = 1000L * 60 * 15;
    private int MAX_NICKNAME_NUMBER = 999;
    private int MIN_NICKNAME_NUMBER = 1;


    @Value("${default.img.url.profile}")
    private static String DEFAULT_PROFILE_IMG;

    @Override
    @Transactional
    public Tokens createUser(String tempToken, MultipartFile multipartFile, SignUpRequest request) {
        // temp token을 사용하여 social 정보 가져오기
        UUID socialUID = UUID.fromString(tempToken);
        Social socialInfo = socialRepository.findByTemporalToken(socialUID).orElseThrow(() -> new GeneralException(Code.INVALID_ACCESS_TOKEN));

        redisService.deleteValues(request.getNickname());
        checkNickname(request.getNickname());

        String profileImg = DEFAULT_PROFILE_IMG;

        if(multipartFile != null) {
            // profileImg를 이미지 파일로 받았다면
            // TODO:
            //  s3 이미지 업로드 후 생성 된 url로 profileImg url 변경
        } else if(request.getProfileImg() != null) {
            // profileImg를 이미지로 받지 않고, url로 받았다면
            profileImg = request.getProfileImg();
        }

        System.out.println(profileImg);

        // user 생성
        User user = User.builder()
                .userId(UUID.randomUUID())
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
        Tokens tokens = jwtService.createToken(String.valueOf(user.getUserId()));
        redisService.setValuesWithTimeout(tokens.getRefreshToken(), String.valueOf(user.getUserId()), JwtConfig.REFRESH_TOKEN_VALID_TIME);

        return tokens;
    }

    @Override
    public void checkNickname(String nickname) {
        // redis 내 검색
        redisService.getValues(nickname).ifPresent(a -> {
            throw new GeneralException(Code.USER_DUPLICATE_NICKNAME);
        });

        // DB 내 검색
        if(userRepository.countUsersByNicknameAndMemberStatusIs(nickname, User.MemberStatus.ACTIVE) > 0) {
            throw new GeneralException(Code.USER_DUPLICATE_NICKNAME);
        }
    }

    @Override
    public void confirmNickname(String nickname) {
        checkNickname(nickname);
        redisService.setValuesWithTimeout(nickname, "null", NICKNAME_EXPIRED_TIME);
    }

    public String generateRandomNickname() {
        String nickname = null;

        // 중복 없는 닉네임이 생성될 때까지 반복
        while (true) {
            try {
                // 단어 두 개 선택
                String[] nicknames = NicknameBucket.getNicknames();

                // MIN_NICKNAME_NUMBER : 1 ~ MAX_NICKNAME_NUMBER : 999 까지 수 중 랜덤 수 생성
                int random = (int) (Math.random() * (MAX_NICKNAME_NUMBER - MIN_NICKNAME_NUMBER) + MIN_NICKNAME_NUMBER);

                nickname = nicknames[0] + " " + nicknames[1] + " " + String.valueOf(random);

                checkNickname(nickname);
            } catch (RuntimeException e) {
                continue;
            }
            break;
        }

        return nickname;
    }

    @Override
    public ProfileResponse getProfile(User user, String nickname) {
        User target = userRepository.findByNicknameAndMemberStatusIs(nickname, User.MemberStatus.ACTIVE)
                .orElseThrow(() -> new GeneralException(Code.DONT_EXIST_USER));

        AtomicBoolean followed = new AtomicBoolean(false);

        if(user != null) {
            followRepository.findByFollowerAndFollowing(user, target).ifPresent(a -> {
                followed.set(true);});
        }

        return ProfileResponse.builder()
                .nickname(target.getNickname())
                .review(target.getReviewCnt())
                .pin(target.getPinCnt())
                .follower(target.getFollower())
                .followed(followed.get())
                .build();
    }

    @Override
    @Transactional
    public void updateNickname(User user, String nickname) {
        redisService.deleteValues(nickname);
        checkNickname(nickname);

        user.updateNickname(nickname);

        userRepository.save(user);
    }

    @Override
    public void updateProfile(User user, MultipartFile profileImg, UpdateProfileRequest request) {
        if(profileImg != null) {
            s3Service.deleteImageFromUrl(user.getProfileImage());
            String newProfile = s3Service.uploadImage(profileImg);

            user.updateProfile(newProfile);
        }

        if(request != null) {
            if(request.getAge() != null) {
                user.updateAge(User.Age.valueOf(request.getAge()));
            }

            if(request.getGender() != null) {
                user.updateGender(User.Gender.valueOf(request.getGender()));
            }
        }

        userRepository.save(user);
    }

    @Override
    public PublishingInfoResponse getPublishingInfo(User user) {
        return PublishingInfoResponse.builder()
                .publishReview((user.getPublishReview() == PublishStatus.PUBLIC) ? true : false)
                .publishPin((user.getPublishPin() == PublishStatus.PUBLIC) ? true : false)
                .build();
    }

    @Override
    public void updatePublishingInfo(User user, PublishingInfoRequest request) {
        PublishStatus reviewStatus = (request.getPublishReview()) ?PublishStatus.PUBLIC : PublishStatus.PRIVATE;
        PublishStatus pinStatus = (request.getPublishPin()) ? PublishStatus.PUBLIC : PublishStatus.PRIVATE;;

        user.updatePublishReview(reviewStatus);
        user.updatePublishPin(pinStatus);

        userRepository.save(user);
    }
}
