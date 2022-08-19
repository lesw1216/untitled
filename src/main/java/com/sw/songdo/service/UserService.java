package com.sw.songdo.service;

import com.sw.songdo.dto.UserResDTO;
import com.sw.songdo.entity.UserEntity;
import com.sw.songdo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public UserEntity save(UserResDTO userResDTO) {
        return repository.save(userResDTO);
    }
}
