package com.example.restfulwebservice.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
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
@JsonFilter("UserInfoV2")
public class UserV2 extends User{

    private String grade;

}
