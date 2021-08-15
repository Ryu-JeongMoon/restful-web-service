package com.example.restfulwebservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @JsonIgnore
    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
