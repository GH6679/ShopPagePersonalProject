package com.example.demo.controller;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    //================================================================
    //통합 검색
    //================================================================
    @GetMapping("/find")
    public String search_get(){

        return "/find";

    }
    @PostMapping("/find")
    public String search_post(String searchkeyword, Model model){

        System.out.println("/Post find..."+searchkeyword);
        model.addAttribute("key",searchkeyword);

        return null;

    }

}
