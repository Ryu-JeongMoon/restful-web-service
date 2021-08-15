package com.example.restfulwebservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * class level 에서 제외시킬 속성 정의 가능
 */
// @JsonIgnoreProperties(value = {"password", "ssn"})
//@JsonFilter("UserInfo")
@Data
@Entity
@NoArgsConstructor
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Size(min = 2, message = "enter name at least 2 letters")
    @ApiModelProperty(notes = "사용자 이름을 입력해주세요.")
    private String name;

    @Past
    @ApiModelProperty(notes = "사용자 등록일을 입력해주세요.")
    private LocalDateTime joinDate;

    //@JsonIgnore
    @ApiModelProperty(notes = "사용자 비밀번호를 입력해주세요.")
    private String password;

    @ApiModelProperty(notes = "사용자 사회보장번호를 입력해주세요.")
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(Long id, String name, LocalDateTime joinDate, String password, String ssn, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
        this.posts = posts;
    }
}
