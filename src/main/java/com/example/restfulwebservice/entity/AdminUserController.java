package com.example.restfulwebservice.entity;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final UserDaoService userDaoService;


    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        return getMappingJacksonValue(userDaoService.findAll(), SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn"), "UserInfo");
    }

    /** Filter 를 이용하여 domain 을 건드리지 않고 client 로 보낼 데이터 선별
     *  음.. 근데 Entity 가 변하면 스펙이 바뀌는 것은 마찬가지니까
     *  맘 편하게 DTO 만들어서 반환하는 것이 좋지 않을까?
     *  com.fasterxml.jackson.databind 에 의존성이 생기는 것도 문제?
     */
    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieveUserV1(@PathVariable Long id) {
        return userDaoService.findOne(id)
                             .map(user -> getMappingJacksonValue(user, SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn"), "UserInfo"))
                             .orElseThrow(() -> new UserNotFoundException("id 없음"));
    }

    /** API version 관리
     *  1. URL 에 삽입
     *  2. URL 뒤에 query-string 이용
     *  3. Header 이용, header = "key=value" 형식 사용
     *  4. Header - Accept 이용, produces = "" 로 만들어내는 형식 지정
     */
    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}", params = "v2")
    //@GetMapping(value = "/users/{id}", headers = "api-v=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.app-v2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable Long id) {
        return userDaoService.findOne(id)
                             .map(user -> {
                                 UserV2 userV2 = new UserV2();
                                 BeanUtils.copyProperties(user, userV2);
                                 userV2.setGrade("VIP");
                                 return getMappingJacksonValue(userV2, SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade"), "UserInfoV2");
                             })
                             .orElseThrow(() -> new UserNotFoundException("id 없음"));
    }

    private MappingJacksonValue getMappingJacksonValue(User user, SimpleBeanPropertyFilter filter, String userInfo) {

        FilterProvider filters = new SimpleFilterProvider().addFilter(userInfo, filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    private MappingJacksonValue getMappingJacksonValue(List<User> users, SimpleBeanPropertyFilter filter, String userInfo) {

        FilterProvider filters = new SimpleFilterProvider().addFilter(userInfo, filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
}
