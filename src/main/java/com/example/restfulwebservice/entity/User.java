package com.example.restfulwebservice.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/** class level 에서 제외시킬 속성 정의 가능 */
// @JsonIgnoreProperties(value = {"password", "ssn"})

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("UserInfo")
public class User {

    private Long id;

    @Size(min = 2, message = "enter name at least 2 letters")
    private String name;

    @Past
    private LocalDateTime joinDate;

    //@JsonIgnore
    private String password;

    private String ssn;

}
