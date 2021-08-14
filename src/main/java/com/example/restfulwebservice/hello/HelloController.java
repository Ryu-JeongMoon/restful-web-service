package com.example.restfulwebservice.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final MessageSource messageSource;

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

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
