package com.example.restfulwebservice.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    // 500 error 그대로 노출하면 trace 로 인해 코드 정보 다 나옴. 보안상 위험.
    // 사용자의 실수로 잘 못 요청되는 경우 500 -> 404 로 바꿔주고 trace 안 나오게끔 바꿔주자.
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable Long id) {
        return userDaoService.findOne(id)
                             .orElseThrow(() -> new UserNotFoundException("id 없음"));
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) {

        Long savedId = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(savedId)
                                                  .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        boolean isDeleted = userDaoService.deleteById(id);

        if (isDeleted)
            return ResponseEntity.ok("OK~~");
        return ResponseEntity.ok("No ID");
    }
}
