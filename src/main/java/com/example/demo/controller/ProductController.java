package com.example.demo.controller;


import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.dto.ProductKeywordDto;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //프로젝트내부에 이미지를 저장하기위한 임시
//    @Autowired
//    private ResourceLoader resourceLoader;


    @GetMapping("/index")
    public String product_index() {

        return "product/index";
    }

    @GetMapping("/set")
    public void product_set() {
        log.info("GET /product/set...");
    }

    @PostMapping("/set")
    public String product_post(ProductDto dto, @RequestParam("files") MultipartFile[] files) throws IOException {

        productService.setProduct(dto,files);

        return "redirect:/product/index";
    }


    @GetMapping("/get/{no}")
    public String product_get(@PathVariable Long no, Model model) throws IOException {

        log.info("GET /product/get..."+no);
        ProductDto Proddto = productService.getProductOne(no);

        //키워드 목록을 불러온다.
        List<ProductKeywordDto> keydto = productService.getKeywordList();


        //상품 하나하나에 키워드를 적용

            List<String> explain = new ArrayList<>();
            String[] explaintoString;
            List<String> keywords = new ArrayList<>();
            String[] keywordstoString;

            //해당하는 키워드가 있는지 검색
            for(int j=0;j<keydto.size();j++){

                //해당 제목과 동일한 키워드가 있는지 확인
                boolean iskey = Proddto.getProdname().contains(keydto.get(j).getKeywname());

                if(iskey){

                    explain.add(keydto.get(j).getKeywtext());
                    keywords.add(keydto.get(j).getKeywname());

                }else{



                }

            }


            //리스트를 String 배열로 변경
            explaintoString = explain.toArray(new String[0]);
            keywordstoString = keywords.toArray(new String[0]);

            //리스트에 등록
            Proddto.setProdkeywords(keywordstoString);
            Proddto.setProdexplains(explaintoString);

            log.info(Proddto.getProdname() +" 해당 키워드 : "+ Arrays.toString(Proddto.getProdkeywords())+ " 내용 : " + Arrays.toString(Proddto.getProdexplains()));

            //base64 인코딩
            if(!(Proddto.getProdimagepaths() ==null)){

                List<String> base64encodelist = new ArrayList<>();
                String[] base64encodeimg;

                for(String img : Proddto.getProdimagepaths()){

                    Path imgpath = Paths.get(img);
                    byte[] imageData = Files.readAllBytes(imgpath);

                    String base64encode = Base64.getEncoder().encodeToString(imageData);
                    base64encodelist.add("data:image/jpeg;base64," + base64encode);

                }
                base64encodeimg = base64encodelist.toArray(new String[0]);
                Proddto.setProdimagepaths(base64encodeimg);


            }


        model.addAttribute("productDto",Proddto);
        return "product/get";

    }


    @GetMapping("/update")
    public String product_update(Long no,Model model) throws IOException {

        log.info("GET /product/get..."+no);
        ProductDto Proddto = productService.getProductOne(no);

        //키워드 목록을 불러온다.
        List<ProductKeywordDto> keydto = productService.getKeywordList();


        //상품 하나하나에 키워드를 적용

        List<String> explain = new ArrayList<>();
        String[] explaintoString;
        List<String> keywords = new ArrayList<>();
        String[] keywordstoString;

        //해당하는 키워드가 있는지 검색
        for(int j=0;j<keydto.size();j++){

            //해당 제목과 동일한 키워드가 있는지 확인
            boolean iskey = Proddto.getProdname().contains(keydto.get(j).getKeywname());

            if(iskey){

                explain.add(keydto.get(j).getKeywtext());
                keywords.add(keydto.get(j).getKeywname());

            }else{



            }

        }


        //리스트를 String 배열로 변경
        explaintoString = explain.toArray(new String[0]);
        keywordstoString = keywords.toArray(new String[0]);

        //리스트에 등록
        Proddto.setProdkeywords(keywordstoString);
        Proddto.setProdexplains(explaintoString);

        log.info(Proddto.getProdname() +" 해당 키워드 : "+ Arrays.toString(Proddto.getProdkeywords())+ " 내용 : " + Arrays.toString(Proddto.getProdexplains()));

        //base64 인코딩
        if(!(Proddto.getProdimagepaths() ==null)){

            List<String> base64encodelist = new ArrayList<>();
            String[] base64encodeimg;

            for(String img : Proddto.getProdimagepaths()){

                Path imgpath = Paths.get(img);
                byte[] imageData = Files.readAllBytes(imgpath);

                String base64encode = Base64.getEncoder().encodeToString(imageData);
                base64encodelist.add("data:image/jpeg;base64," + base64encode);

            }
            base64encodeimg = base64encodelist.toArray(new String[0]);
            Proddto.setProdimagepaths(base64encodeimg);


        }


        model.addAttribute("productDto",Proddto);
        return "product/update";

    }



    @GetMapping("/keyword/set")
    public void product_keyword() {

    }

    @PostMapping("/keyword/set")
    public String product_keyword_post(ProductKeywordDto dto) {

        //null값 방지
        if (dto.getKeywname() != null && dto.getKeywtext() != null && !dto.getKeywname().equals("") && !dto.getKeywtext().equals("")) {
            log.info("POST /product/inkeyword..." + dto);
            dto.setKeywno(null);
            productService.setKeyword(dto);
        } else {
            log.info("POST /product/inkeyword...no data");
            return "redirect:/product/keyword/set";
        }
        return "redirect:/product/keyword/set";

    }
}