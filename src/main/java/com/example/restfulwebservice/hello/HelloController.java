package com.example.restfulwebservice.hello;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello-world")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/hello-world-bean")
    public ResponseEntity<HelloBean> helloBean() {
        return ResponseEntity.ok(new HelloBean("hello"));
    }

    @GetMapping("/hello-world-bean/path-variable/{name}")
    public ResponseEntity<HelloBean> helloBeanPathVariable(@PathVariable("name") String name) {
        return ResponseEntity.ok(new HelloBean(String.format("hello, %s", name)));
    }
}
