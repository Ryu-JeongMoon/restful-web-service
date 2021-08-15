package com.example.restfulwebservice.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jpa")
public class UserJpaController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        initDB();
    }

    @Transactional
    public void initDB() {
        User user1 = User.builder()
                         .id(1000L)
                         .name("user1")
                         .password("pass1")
                         .joinDate(LocalDateTime.now())
                         .ssn("101010-1010101")
                         .build();
        User user2 = User.builder()
                         .id(1001L)
                         .name("user2")
                         .password("pass2")
                         .joinDate(LocalDateTime.now())
                         .ssn("202020-2020202")
                         .build();
        User user3 = User.builder()
                         .id(1002L)
                         .name("user3")
                         .password("pass3")
                         .joinDate(LocalDateTime.now())
                         .ssn("303030-3030303")
                         .build();
        User user4 = User.builder()
                         .id(1003L)
                         .name("user4")
                         .password("pass4")
                         .joinDate(LocalDateTime.now())
                         .ssn("404040-4040404")
                         .build();

        Post post1 = Post.builder()
                         .id(1000L)
                         .description("content1")
                         .user(user1)
                         .build();
        Post post2 = Post.builder()
                         .id(1001L)
                         .description("content2")
                         .user(user1)
                         .build();
        Post post3 = Post.builder()
                         .id(1002L)
                         .description("content3")
                         .user(user3)
                         .build();
        Post post4 = Post.builder()
                         .id(1003L)
                         .description("content4")
                         .user(user4)
                         .build();

        postRepository.saveAll(Arrays.asList(post1, post2, post3, post4));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> retrieveAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel> retrieveOneUser(@PathVariable Long id) {

        EntityModel<User> entityModel = new EntityModel<>(userRepository.findById(id)
                                                                        .orElseThrow(() -> new UserNotFoundException("no!")));
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users"));
        return ResponseEntity.ok(entityModel);
    }

    /**
     * 오호.. EntityModel<List<Post>> 요렇게 하니까
     * CollectionModel 쓰라고 경고 뜬당
     */
    @GetMapping("/users/{id}/posts")
    public ResponseEntity<CollectionModel> retrievePost(@PathVariable Long id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new UserNotFoundException("no!"));

        CollectionModel<Post> entityModel = new CollectionModel<>(user.getPosts());

        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users"));
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveOneUser(user.getId())).withRel("user"));

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Long id, @RequestBody Post post) {
        post.setUser(userRepository.findById(id)
                                   .orElseThrow(() -> new UserNotFoundException("no")));

        Post savedPost = postRepository.save(post);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                                             .path("id")
                                             .buildAndExpand(savedPost.getId())
                                             .toUri();

        return ResponseEntity.created(uri).build();
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<EntityModel> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        EntityModel<User> entityModel = new EntityModel<>(savedUser);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users"));
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveOneUser(savedUser.getId())).withRel("user"));
        return ResponseEntity.ok(entityModel);
    }

}
