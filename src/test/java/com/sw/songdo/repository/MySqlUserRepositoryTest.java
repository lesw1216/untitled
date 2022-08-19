package com.sw.songdo.repository;

import com.sw.songdo.dto.UserResDTO;
import com.sw.songdo.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
class MySqlUserRepositoryTest {

    private final UserRepository repository;

    @Autowired
    MySqlUserRepositoryTest(UserRepository repository) {
        this.repository = repository;
    }


    @Test
    void save() {
        UserResDTO user = new UserResDTO();
        user.setUsername("test");
        user.setPassword("1234");
        user.setName("테스터");

        UserEntity saveUser = repository.save(user);
        //assertThat(saveUser.getUsername()).isEqualTo("test");
    }

    @Test
    void findById() {
    }
}