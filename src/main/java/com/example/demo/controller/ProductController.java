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

    //================================================================
    //상품 등록
    //================================================================
    @GetMapping("/set")
    public void product_set() {
        log.info("GET /product/set...");
    }

    @PostMapping("/set")
    public String product_post(ProductDto dto, @RequestParam("files") MultipartFile[] files) throws IOException {

        log.info("확인용"+dto);
        productService.setProduct(dto,files);

        return "redirect:/product/index";
    }


    //================================================================
    //상품 상세 보기
    //================================================================
    @GetMapping("/get/{no}")
    public String product_get(@PathVariable Long no, Model model) throws IOException {

        log.info("GET /product/get..."+no);
        ProductDto Proddto = productService.getProductOne(no);

        //키워드 목록을 불러온다.
        List<ProductKeywordDto> keydto = productService.getKeywordList();


        //키워드 적용 로직
        //검색한 키워드를 String 배열로 변환하기 위한 변수들
        List<String> explain = new ArrayList<>();   //키워드 설명
        String[] explaintoString;
        List<String> keywords = new ArrayList<>();  //키워드 이름
        String[] keywordstoString;


        //키워드 이름과 동일한 문자를 가지고있는지 검색하는 로직
        for(int j=0;j<keydto.size();j++){

            //상품 제목에서 해당하는 키워드가 있는지 검색
            boolean iskey = Proddto.getProdname().contains(keydto.get(j).getKeywname());
            //상품 테그에서 해당하는 키워드가 있는지 검색
            boolean istag = false;

            for(int t = 0 ;t < Proddto.getProdtags().length ;t++){

                String[] tags = Proddto.getProdtags();
                boolean gettag = tags[t].contains(keydto.get(j).getKeywname());

                if (gettag) {
                    istag = gettag;
                    break;
                }else {}
            }

            //일치하는 키워드가 있을 경우 해당 키워드와 설명을 삽입
            if(iskey || istag){

                explain.add(keydto.get(j).getKeywtext());
                keywords.add(keydto.get(j).getKeywname());

            }else{}

        }


        //리스트를 String 배열로 변경
        explaintoString = explain.toArray(new String[0]);
        keywordstoString = keywords.toArray(new String[0]);

        //리스트에 등록
        Proddto.setProdkeywords(keywordstoString);
        Proddto.setProdexplains(explaintoString);

//            log.info(listdto.get(i).getProdname() +" 해당 키워드 : "+Arrays.toString(listdto.get(i).getProdkeywords())+ " 내용 : " + Arrays.toString(listdto.get(i).getProdexplains()));

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

    //================================================================
    //상품 정보 수정
    //================================================================

    @GetMapping("/update/{no}")
    public String product_update(@PathVariable Long no,Model model) throws IOException {

        log.info("GET /product/update..."+no);
        ProductDto Proddto = productService.getProductOne(no);

        //키워드 목록을 불러온다.
        List<ProductKeywordDto> keydto = productService.getKeywordList();


        //키워드 적용 로직
        //검색한 키워드를 String 배열로 변환하기 위한 변수들
        List<String> explain = new ArrayList<>();   //키워드 설명
        String[] explaintoString;
        List<String> keywords = new ArrayList<>();  //키워드 이름
        String[] keywordstoString;


        //키워드 이름과 동일한 문자를 가지고있는지 검색하는 로직
        for(int j=0;j<keydto.size();j++){

            //상품 제목에서 해당하는 키워드가 있는지 검색
            boolean iskey = Proddto.getProdname().contains(keydto.get(j).getKeywname());
            //상품 테그에서 해당하는 키워드가 있는지 검색
            boolean istag = false;

            for(int t = 0 ;t < Proddto.getProdtags().length ;t++){

                String[] tags = Proddto.getProdtags();
                boolean gettag = tags[t].contains(keydto.get(j).getKeywname());

                if (gettag) {
                    istag = gettag;
                    break;
                }else {}
            }

            //일치하는 키워드가 있을 경우 해당 키워드와 설명을 삽입
            if(iskey || istag){

                explain.add(keydto.get(j).getKeywtext());
                keywords.add(keydto.get(j).getKeywname());

            }else{}

        }


        //리스트를 String 배열로 변경
        explaintoString = explain.toArray(new String[0]);
        keywordstoString = keywords.toArray(new String[0]);

        //리스트에 등록
        Proddto.setProdkeywords(keywordstoString);
        Proddto.setProdexplains(explaintoString);

//            log.info(listdto.get(i).getProdname() +" 해당 키워드 : "+Arrays.toString(listdto.get(i).getProdkeywords())+ " 내용 : " + Arrays.toString(listdto.get(i).getProdexplains()));

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

    //================================================================
    //결제 성공여부 이동 페이지
    //================================================================
    @GetMapping("/payment/successinfo")
    public void successinfo() {}
    @GetMapping("/payment/cancelinfo")
    public void cancelinfo() {}
    @GetMapping("/payment/failinfo")
    public void failinfo() {}


    //================================================================
    //키워드 등록
    //================================================================
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

    @GetMapping("/cartinfo")
    public void product_cartinfo(){

    }
}