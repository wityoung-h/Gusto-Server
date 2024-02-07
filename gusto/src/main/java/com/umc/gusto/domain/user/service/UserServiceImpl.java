package com.umc.gusto.domain.user.service;

import com.umc.gusto.domain.user.entity.Follow;
import com.umc.gusto.domain.user.entity.Social;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.model.NicknameBucket;
import com.umc.gusto.domain.user.model.request.SignUpRequest;
import com.umc.gusto.domain.user.model.response.FollowResponse;
import com.umc.gusto.domain.user.model.response.ProfileRes;
import com.umc.gusto.domain.user.repository.FollowRepository;
import com.umc.gusto.domain.user.repository.SocialRepository;
import com.umc.gusto.domain.user.repository.UserRepository;
import com.umc.gusto.global.auth.JwtService;
import com.umc.gusto.global.auth.model.Tokens;
import com.umc.gusto.global.config.secret.JwtConfig;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import com.umc.gusto.global.exception.customException.NotFoundException;
import com.umc.gusto.global.util.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final SocialRepository socialRepository;
    private final JwtService jwtService;
    private final RedisService redisService;
    private final FollowRepository followRepository;

    private static final long NICKNAME_EXPIRED_TIME = 1000L * 60 * 15;
    private static final int MAX_NICKNAME_NUMBER = 999;
    private static final int MIN_NICKNAME_NUMBER = 1;
    private static final int FOLLOW_LIST_PAGE = 30;


    @Value("${default.img.url.profile}")
    private String DEFAULT_IMG;


    @Override
    @Transactional
    public Tokens createUser(String tempToken, MultipartFile multipartFile, SignUpRequest request) {
        // temp token을 사용하여 social 정보 가져오기
        UUID socialUID = UUID.fromString(tempToken);
        Social socialInfo = socialRepository.findByTemporalToken(socialUID).orElseThrow(() -> new GeneralException(Code.INVALID_ACCESS_TOKEN));

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
    @Transactional(readOnly = true)
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
    @Transactional
    public void confirmNickname(String nickname) {
        checkNickname(nickname);
        redisService.setValuesWithTimeout(nickname, "null", NICKNAME_EXPIRED_TIME);
    }

    @Override
    public String generateRandomNickname() {
        String nickname;

        // 중복 없는 닉네임이 생성될 때까지 반복
        while (true) {
            try {
                // 단어 두 개 선택
                String[] nicknames = NicknameBucket.getNicknames();

                // MIN_NICKNAME_NUMBER : 1 ~ MAX_NICKNAME_NUMBER : 999 까지 수 중 랜덤 수 생성
                int random = (int) (Math.random() * (MAX_NICKNAME_NUMBER - MIN_NICKNAME_NUMBER) + MIN_NICKNAME_NUMBER);

                nickname = nicknames[0] + " " + nicknames[1] + " " + random;

                checkNickname(nickname);
            } catch (RuntimeException e) {
                continue;
            }
            break;
        }

        return nickname;
    }

    @Override
    public ProfileRes getProfile(String nickname) {
        User user = userRepository.findByNicknameAndMemberStatusIs(nickname, User.MemberStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException(Code.USER_NOT_FOUND));
        return new ProfileRes(user.getNickname(), user.getReviewCnt(), user.getPinCnt(), user.getFollower());
    }

    @Override
    @Transactional
    public void followUser(User user, String nickname) {
        User target = userRepository.findByNicknameAndMemberStatusIs(nickname, User.MemberStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException(Code.USER_NOT_FOUND));

        // 팔로우 대상이 자기 자신인지 check
        if(target.getUserId().equals(user.getUserId())) {
            throw new GeneralException(Code.USER_FOLLOW_SELF);
        }

        // 이미 follow한 내역이 있는지 check
        followRepository.findByFollowerAndFollowing(user, target)
                .ifPresent(follow -> {
                    throw new GeneralException(Code.USER_FOLLOW_ALREADY);
                });

        Follow newFollow = Follow.builder()
                .follower(user)
                .following(target)
                .build();

        followRepository.save(newFollow);

        // target의 팔로워 수 1 증가
        target.updateFollower(target.getFollower() + 1);
        userRepository.save(target);
    }

    @Override
    @Transactional
    public void unfollowUser(User user, String nickname) {
        User target = userRepository.findByNicknameAndMemberStatusIs(nickname, User.MemberStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException(Code.USER_NOT_FOUND));

        Follow followInfo = followRepository.findByFollowerAndFollowing(user, target)
                .orElseThrow(() -> new NotFoundException(Code.USER_FOLLOW_NOT_FOUND));

        followRepository.delete(followInfo);

        // target의 팔로워 수 1 감소
        target.updateFollower(target.getFollower() - 1);
        userRepository.save(target);
    }

    @Override
    @Transactional
    public List<FollowResponse> getFollowList(User user, Long followId) {
        if(followId == null) {
            followId = 0L;
        }

        //follow 목록 조회
        List<Follow> followList = followRepository.findFollowList(user, followId, Pageable.ofSize(FOLLOW_LIST_PAGE));

        // 반환할 목록이 없음 throw Exception
        if(followList.size() == 0) {
            throw new NotFoundException(Code.USER_FOLLOW_NO_MORE_CONTENT);
        }

        // res mapping
        List<FollowResponse> response = followList.stream()
                .map(follow -> {
                    FollowResponse item = FollowResponse.builder()
                            .followId(follow.getFollowId())
                            .nickname(follow.getFollowing().getNickname())
                            .profileImg(follow.getFollowing().getProfileImage())
                            .build();

                    return item;
                })
                .collect(Collectors.toList());

        return response;
    }

    @Override
    @Transactional
    public List<FollowResponse> getFollwerList(User user, Long followId) {
        if(followId == null) {
            followId = 0L;
        }

        List<Follow> followList = followRepository.findFollwerList(user, followId, Pageable.ofSize(FOLLOW_LIST_PAGE));

        // 반환할 목록이 없음 throw Exception
        if(followList.size() == 0) {
            throw new NotFoundException(Code.USER_FOLLOW_NO_MORE_CONTENT);
        }

        // res mapping
        List<FollowResponse> response = followList.stream()
                .map(follow -> {
                    FollowResponse item = FollowResponse.builder()
                            .followId(follow.getFollowId())
                            .nickname(follow.getFollowing().getNickname())
                            .profileImg(follow.getFollowing().getProfileImage())
                            .build();

                    return item;
                })
                .collect(Collectors.toList());

        return response;
    }
}
