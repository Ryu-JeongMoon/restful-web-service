package com.example.restfulwebservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private LocalDateTime errorDateTime;
    private String message;
    private String details;
}

/**
 * 노출하고 싶은 정보만 객체를 생성해서 보여준다
 */