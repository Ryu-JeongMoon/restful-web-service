package com.example.restfulwebservice.entity;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static long userCount = 3;

    static {
        users.add(User.builder()
                      .id(1L)
                      .name("hellen")
                      .joinDate(LocalDateTime.now())
                      .password("pass1")
                      .ssn("701010-1111111")
                      .posts(null)
                      .build());

        users.add(User.builder()
                      .id(2L)
                      .name("michelle")
                      .joinDate(LocalDateTime.now())
                      .password("pass2")
                      .ssn("701010-2222222")
                      .posts(null)
                      .build());

        users.add(User.builder()
                      .id(3L)
                      .name("kevin")
                      .joinDate(LocalDateTime.now())
                      .password("pass3")
                      .ssn("701010-3333333")
                      .posts(null)
                      .build());
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findOne(Long id) {
        return users.stream()
                    .parallel()
                    .filter(user -> id == user.getId())
                    .findAny();
    }

    public Long save(User user) {
        if (user.getId() == null)
            user.setId(++userCount);

        for (User _user : users) {
            if (user.getId() == _user.getId())
                throw new RuntimeException("아이디 중복");
        }

        users.add(user);
        return user.getId();
    }

    public boolean deleteById(Long id) {
        return users.removeIf(user -> (user.getId() == id));
    }
}
