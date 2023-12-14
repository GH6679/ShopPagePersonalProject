package com.example.demo.restcontroller;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/search")
public class SearchRestController {

    @Autowired
    private SearchService searchService;

    //================================================================
    //상품 전체 검색
    //================================================================
    @PostMapping("/list/product")
    public List<ProductDto> search_list_product(@RequestBody Map<String, String> searchKeyword) throws IOException {

        String keyword = searchKeyword.get("keyword");
        log.info("검색어 : "+keyword);
        List<ProductDto> listdto = searchService.search_all(keyword);


        for(int i=0;i<listdto.size();i++){

            //base64 인코딩
            if(!(listdto.get(i).getProdimagepaths() ==null)){

                List<String> base64encodelist = new ArrayList<>();
                String[] base64encodeimg;

                for(String img : listdto.get(i).getProdimagepaths()){

                    Path imgpath = Paths.get(img);
                    byte[] imageData = Files.readAllBytes(imgpath);

                    String base64encode = Base64.getEncoder().encodeToString(imageData);
                    base64encodelist.add("data:image/jpeg;base64," + base64encode);

                }
                base64encodeimg = base64encodelist.toArray(new String[0]);
                listdto.get(i).setProdimagepaths(base64encodeimg);


            }

        }

        return listdto;

    }

    //================================================================
    //상품 테그 검색
    //================================================================
    @PostMapping("/list/tag")
    public List<ProductDto> search_list_tag(@RequestBody Map<String, String> searchKeyword) throws IOException {

        String keyword = searchKeyword.get("keyword");
        List<ProductDto> listdto = searchService.search_tag(keyword);
        System.out.println("검색 결과 : "+listdto);

        for(int i=0;i<listdto.size();i++){

            //base64 인코딩
            if(!(listdto.get(i).getProdimagepaths() ==null)){

                List<String> base64encodelist = new ArrayList<>();
                String[] base64encodeimg;

                for(String img : listdto.get(i).getProdimagepaths()){

                    Path imgpath = Paths.get(img);
                    byte[] imageData = Files.readAllBytes(imgpath);

                    String base64encode = Base64.getEncoder().encodeToString(imageData);
                    base64encodelist.add("data:image/jpeg;base64," + base64encode);

                }
                base64encodeimg = base64encodelist.toArray(new String[0]);
                listdto.get(i).setProdimagepaths(base64encodeimg);


            }

        }

        return listdto;

    }
}
