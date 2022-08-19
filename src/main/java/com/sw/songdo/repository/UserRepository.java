package com.sw.songdo.repository;

import com.sw.songdo.dto.UserResDTO;
import com.sw.songdo.entity.UserEntity;

public interface UserRepository {

    UserEntity save(UserResDTO userResDTO);


    UserEntity findByUsername(String username);
}
