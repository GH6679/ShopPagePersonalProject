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
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductRestController {
    @Autowired
    private ProductService productService;


    //================================================================
    //상품 목록 출력
    //================================================================
    @GetMapping("/list")
    public List<ProductDto> product_list(@RequestParam String prodtype, @RequestParam String prodname ,@RequestParam List<String> prodtags) throws IOException {

        System.out.println("상품 타입 : "+prodtype+" 검색어 : "+prodname+" 검색 테그필터 : "+prodtags);

        //상품목록 을 불러온다.
        List<ProductDto> listdto = productService.getProductList(prodtype,prodname,prodtags);
        //키워드 목록을 불러온다.
        List<ProductKeywordDto> keydto = productService.getKeywordList();


        //키워드 적용 로직
        for(int i=0;i<listdto.size();i++){

            //검색한 키워드를 String 배열로 변환하기 위한 변수들
            List<String> explain = new ArrayList<>();   //키워드 설명
            String[] explaintoString;
            List<String> keywords = new ArrayList<>();  //키워드 이름
            String[] keywordstoString;


            //키워드 이름과 동일한 문자를 가지고있는지 검색하는 로직
            for(int j=0;j<keydto.size();j++){

                //상품 제목에서 해당하는 키워드가 있는지 검색
                boolean iskey = listdto.get(i).getProdname().contains(keydto.get(j).getKeywname());
                //상품 테그에서 해당하는 키워드가 있는지 검색
                boolean istag = false;

                for(int t = 0 ;t < listdto.get(i).getProdtags().length ;t++){

                    String[] tags = listdto.get(i).getProdtags();
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
            listdto.get(i).setProdkeywords(keywordstoString);
            listdto.get(i).setProdexplains(explaintoString);

//            log.info(listdto.get(i).getProdname() +" 해당 키워드 : "+Arrays.toString(listdto.get(i).getProdkeywords())+ " 내용 : " + Arrays.toString(listdto.get(i).getProdexplains()));

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
    //상품 삭제
    //================================================================
    @DeleteMapping("/delete/{no}")
    public void product_delete(@PathVariable Long no){
        log.info("DELETE /product/delete..."+no);
        productService.deleteProduct(no);

    }

    //================================================================
    //상품 수정
    //================================================================
    @PutMapping("/update/{no}")
    public void product_put(@PathVariable Long no){



    }


    //================================================================
    //키워드 목록 출력
    //================================================================
    @GetMapping("/keyword/list")
    public List<ProductKeywordDto> getKeyword_List(){
        List<ProductKeywordDto> list = productService.getKeywordList();

        return list;
    }
    //================================================================
    //키워드 삭제
    //================================================================
    @DeleteMapping("/keyword/delete/{no}")
    public void product_keyword_delete(@PathVariable int no){
        log.info("DELETE /product/keyword/delete..."+no);

        productService.deleteKeyword(no);
    }
    //================================================================
    //키워드 수정
    //================================================================
    @PutMapping("/keyword/update")
    public void product_keyword_update(ProductKeywordDto dto){

        log.info("PUT /product/keyword/update..."+dto);

        productService.updateKeyword(dto);

    }

    //================================================================
    //장바구니에 상품 불러오기
    //================================================================
    @PostMapping("/cartlist")
    public List<ProductDto> product_cartlist(@RequestBody Map<String,String> username) throws IOException {

        String user = username.get("username");

        List<ProductDto> listdto = productService.getProductCartList(user);

        System.out.println(listdto.size());

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
    //장바구니에 상품추가
    //================================================================
    @PostMapping("/intocart")
    public void product_intocart(@RequestBody Map<String,String> intodata){

        Long prodcode = Long.valueOf(intodata.get("prodcode"));
        String username = intodata.get("username");
        System.out.println("출력 : " + prodcode +" <> "+ username);

        productService.setProductCart(prodcode, username);

    }

    //================================================================
    //장바구니에 상품 제거
    //================================================================
    @PostMapping("/removecart")
    public void product_removecart(@RequestBody Map<String,String> outdata){

        Long prodcode = Long.valueOf(outdata.get("prodcode"));
        String username = outdata.get("username");

        productService.removeProductCart(prodcode , username);

    }

    //================================================================
    //장바구니에 상품 가격 합계 불러오기
    //================================================================
    @PostMapping("/cartprice")
    public Long product_sumCartPrice(@RequestBody Map<String,String> username){
        String user = username.get("username");

        Long price = productService.cartSumOfPrice(user);

        if(price != null){
            return price;
        }else{
            return 0L;
        }

    }


}
