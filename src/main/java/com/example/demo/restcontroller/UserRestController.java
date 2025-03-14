package com.example.demo.restcontroller;

import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;
    @PostMapping("/addr")
    public UserDto addr_get(@RequestBody Map<String,String> username){

        String user = username.get("username");

        UserDto userInfo = userService.getUserInfo(user);
        UserDto dto = new UserDto();

        dto.setAddr1(userInfo.getAddr1());
        dto.setAddr2(userInfo.getAddr2());

        System.out.println("addr1 = "+dto.getAddr1()+" addr2 = "+dto.getAddr2());

        return dto;

    }

}
