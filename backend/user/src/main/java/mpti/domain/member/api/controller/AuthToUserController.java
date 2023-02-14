package mpti.domain.member.api.controller;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import mpti.common.security.UserPrincipal;
import mpti.domain.member.api.request.LoginRequest;
import mpti.domain.member.api.request.SocialSignUpRequest;
import mpti.domain.member.dao.UserRepository;
import mpti.domain.member.entity.User;
import mpti.common.errors.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

/**
 * 인증서버와 통신을 위한  Controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/api/auth")
public class AuthToUserController {
    private static final Logger logger = LoggerFactory.getLogger(AuthToUserController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Gson gson;

    /**
     * 일반 로그인 + 소셜 로그인
     * @param requestBody
     * @return
     */

    @PostMapping("/login")
    public Optional<User> login(@RequestBody String requestBody){
        LoginRequest loginRequest = gson.fromJson(requestBody, LoginRequest.class);
        if(!userRepository.existsByEmail(loginRequest.getEmail())){
            logger.error("이 아이디에 해당하는 트레이너를 찾을 수 없습니다");
            return Optional.empty();
        }
        return Optional.of(userRepository.findUserByEmail(loginRequest.getEmail()));
    }

    /**
     * 일반 회원가입 ( 폼 )
     * @param socialSignUpRequest
     * @return
     */

    @PostMapping("/signup")
    public User registerUser(@Valid @RequestBody SocialSignUpRequest socialSignUpRequest) {
        if(userRepository.existsByEmail(socialSignUpRequest.getEmail())) {
            throw new BadRequestException("이미 사용하고 있는 아이디 이메일입니다");
        }

        User user = new User();
        user.setName(socialSignUpRequest.getName());
        user.setEmail(socialSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(socialSignUpRequest.getPassword()));
        user.setProvider(socialSignUpRequest.getProvider());
        user.setStopUntil(LocalDate.now().minusDays(1));

        return userRepository.saveAndFlush(user);
    }

    /**
     * 이전에 소셜로그인으로 회원가입이 된 사용자 일때
     * 해당 유저의 정보를 가져온다
     * @param body - email
     * @return
     */

    @PostMapping("/update")
    public Optional<User> updateUser(@Valid @RequestBody Map<String , Object> body){
        String email = (String) body.get("email");

        User result = userRepository.findUserByEmail(email);
        if(result == null){
            logger.error("이 아이디에 해당하는 유저를 찾을 수 없습니다");
            return Optional.empty();
        }
        return Optional.of(result);
    }

    /**
     * 권한 테스트
     * @param userPrincipal
     * @return
     */

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> test(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println(userPrincipal.getEmail());
        return ResponseEntity.ok("토큰 테스트 완료");
    }
}
