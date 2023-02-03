package mpti.common.security;

import lombok.RequiredArgsConstructor;

import mpti.domain.member.dao.UserRepository;
import mpti.domain.member.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("이 이메일을 사용하는 회원이 없습니다 : " + email);
        }
        return UserPrincipal.create(user);
    }

}