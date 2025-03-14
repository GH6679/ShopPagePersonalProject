package com.example.demo.controller;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
public class HomeController {



    @GetMapping("/")
    public String index_Page(){
        return "/product/index";
    }



}
