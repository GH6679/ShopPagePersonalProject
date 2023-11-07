package com.example.demo.restcontroller;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.dto.ProductKeywordDto;
import com.example.demo.domain.repository.ProductKeywordRepository;
import com.example.demo.domain.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductRestController {
    @Autowired
    private ProductService productService;


    //상품 목록 페이지
    @GetMapping("/list")
    public List<ProductDto> product_list() throws IOException {
        //상품목록 을 불러온다.
        List<ProductDto> listdto = productService.getProductList();
        //키워드 목록을 불러온다.
        List<ProductKeywordDto> keydto = productService.getKeywordList();


        //상품 하나하나에 키워드를 적용
        for(int i=0;i<listdto.size();i++){

            List<String> explain = new ArrayList<>();
            String[] explaintoString;
            List<String> keywords = new ArrayList<>();
            String[] keywordstoString;

            //해당하는 키워드가 있는지 검색
            for(int j=0;j<keydto.size();j++){

                //해당 제목과 동일한 키워드가 있는지 확인
                boolean iskey = listdto.get(i).getProdname().contains(keydto.get(j).getKeywname());

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
            listdto.get(i).setProdkeywords(keywordstoString);
            listdto.get(i).setProdexplains(explaintoString);

            log.info(listdto.get(i).getProdname() +" 해당 키워드 : "+Arrays.toString(listdto.get(i).getProdkeywords())+ " 내용 : " + Arrays.toString(listdto.get(i).getProdexplains()));

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

    @DeleteMapping("/delete/{no}")
    public void product_delete(@PathVariable Long no){
        log.info("DELETE /product/delete..."+no);
        productService.deleteProduct(no);

    }

    @PutMapping("/update/{no}")
    public void product_put(@PathVariable Long no){



    }



    @GetMapping("/keyword/list")
    public List<ProductKeywordDto> getKeyword_List(){
        List<ProductKeywordDto> list = productService.getKeywordList();

        return list;
    }

    @DeleteMapping("/keyword/delete/{no}")
    public void product_keyword_delete(@PathVariable int no){
        log.info("DELETE /product/keyword/delete..."+no);

        productService.deleteKeyword(no);
    }

    @PutMapping("/keyword/update")
    public void product_keyword_update(ProductKeywordDto dto){

        log.info("PUT /product/keyword/update..."+dto);

        productService.updateKeyword(dto);

    }


}
