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
        users.add(new User(1L, "hellen", LocalDateTime.now()));
        users.add(new User(2L, "michelle", LocalDateTime.now()));
        users.add(new User(3L, "pear", LocalDateTime.now()));
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
            if(user.getId() == _user.getId())
                throw new RuntimeException("아이디 중복");
        }

        users.add(user);
        return user.getId();
    }


}