package com.example.demo.controller.productPayApiController;

import com.example.demo.domain.dto.CartDto;
import com.example.demo.domain.service.ProductService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/product/payment/kakao/pay")
public class KakaoPayApiController {

    @Autowired
    ProductService productService;

    private final String ADMIN_KEY = "d3938680ae3ac0ca401b5f89fffe789a";


//    private final String approval_url = "http://localhost:8080/product/payment/kakao/pay/success";
//    private final String fail_url = "http://localhost:8080/product/payment/kakao/pay/fail";
//    private final String cancel_url = "http://localhost:8080/product/payment/kakao/pay/cancel";

    private final String approval_url = "http://121.150.70.45:52341/product/payment/kakao/pay/success";
    private final String fail_url = "http://121.150.70.45:52341/product/payment/kakao/pay/fail";
    private final String cancel_url = "http://121.150.70.45:52341/product/payment/kakao/pay/cancel";

    @GetMapping("/request")
    public @ResponseBody PaymentResponse  pay(@RequestParam Map<String,String> username){

        String productname = null; //상품목록
        String firstname = null; //상품목록 대표 상품이름
        int pCount = 0; //나머지 상품 갯수
        Long price = 0l; //최종 급액

        //장바구니 목록 불러오기
        String user = username.get("username");
        List<CartDto> listdto = productService.getProductCartList(user);

        if(listdto.size() != 0) {

            for (int i = 0; i < listdto.size(); i++) {

                //첫번째 상품을 상품목록 대표 상품이름으로
                if (i == 0) {

                    //상품 수량이 하나인경우
                    if (listdto.get(i).getCartcount() == 1) {

                        firstname = listdto.get(i).getProdname();

                        price = listdto.get(i).getProdprice() * listdto.get(i).getCartcount();

                        //상품 수량이 여러개 인 경우
                    } else {

                        firstname = listdto.get(i).getProdname();
                        pCount += listdto.get(i).getCartcount() - 1;

                        price = listdto.get(i).getProdprice() * listdto.get(i).getCartcount();

                    }

                //나머지 상품 숫자와 가격 처리
                } else {

                    pCount += listdto.get(i).getCartcount();
                    price += listdto.get(i).getProdprice() * listdto.get(i).getCartcount();

                }

            }

            //카카오페이에 표시할 최종 상품이름
            //상품이 여러개면
            if(pCount > 0) {
                productname = firstname + "...외 " + pCount + "개";

            //상품이 하나면
            }else {
                productname = firstname;
            }


            //URL
            String url = "https://kapi.kakao.com/v1/payment/ready";

            //Header
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
            headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("cid", "TC0ONETIME");
            params.add("partner_order_id", "partner_order_id");
            params.add("partner_user_id", "partner_user_id");
            params.add("item_name", productname);
            params.add("quantity", "1");
            params.add("total_amount", String.valueOf(price));
            params.add("vat_amount", "200");
            params.add("tax_free_amount", "0");
            params.add("approval_url", approval_url);
            params.add("fail_url", fail_url);
            params.add("cancel_url", cancel_url);

            //header + parameter
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

            //Request_Case1
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);
//
//        System.out.println(response);
//        System.out.println(response.getBody());

            //Request_Case2
            RestTemplate rt = new RestTemplate();
            PaymentResponse response = rt.postForObject(url, entity, PaymentResponse.class);
            System.out.println(response);

            return response;
        }

        return null;

    }

    @GetMapping("/success")
    public void success(){log.info("GET /product/payment/kakao/pay/success");}
    @GetMapping("/cancel")
    public void cancel(){ log.info("GET /product/payment/kakao/pay/cancel"); }
    @GetMapping("/fail")
    public void fail(){ log.info("GET /product/payment/kakao/pay/fail"); }

}
//---------------------------------------------------------------
@Data
class PaymentResponse {
    private String tid;
    private boolean tms_result;
    private String next_redirect_app_url;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private String android_app_scheme;
    private String ios_app_scheme;
    private String created_at;
}
