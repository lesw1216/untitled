package com.sw.songdo.custom;

import com.sw.songdo.entity.UserEntity;
import com.sw.songdo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity findUser = repository.findByUsername(username);
        if (findUser == null) {
            throw new UsernameNotFoundException("user not found username = " + username);
        }

        UserDetails user = User.builder()
                .username(findUser.getUsername())
                .password(findUser.getPassword())
                .authorities("USER").build();

        return user;
    }
}
