package com.example.demo.domain.service;

import com.example.demo.domain.dto.CartDto_interface;
import com.example.demo.domain.dto.CartDto;
import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.dto.ProductKeywordDto;
import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.entity.ProductKeyword;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.CartRepository;
import com.example.demo.domain.repository.ProductKeywordRepository;
import com.example.demo.domain.repository.ProductRepository;
import com.example.demo.domain.repository.util.ProductSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class ProductService {

//    //window 용
//    String path = "c:\\etc\\products";
//    //리눅스용
//    String path = "/etc/products";
    String path;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductKeywordRepository productKeywordRepository;

    @Autowired
    private CartRepository cartRepository;
    //================================================================
    //상품 하나를 불러오는 서비스
    //================================================================
    public ProductDto getProductOne(Long no) {
        Optional<Product> optionalProduct = productRepository.findById(no);

        //entity => dto
        Product product = optionalProduct.get();
        ProductDto dto = new ProductDto();
        dto.setProdcode(product.getProdcode());
        dto.setProdauthor(product.getProdauthor());
        dto.setProdtype(product.getProdtype());
        dto.setProdname(product.getProdname());
        dto.setProdtime(product.getProdtime());
        dto.setProdcontext(product.getProdcontext());
        dto.setProdtags(product.getProdtags());
        dto.setProdprice(product.getProdprice());

        //경로와 이미지이름을 합쳐서 dto에 저장
        List<String> imagePaths = new ArrayList<String>();
        String[] imagePathstoString = null;
        String[] entityimageNames = product.getProdimages();

        for(int i=0; i<entityimageNames.length;i++){

            imagePaths.add(product.getProdpath()+File.separator+entityimageNames[i]);

        }
        imagePathstoString = imagePaths.toArray(new String[0]);
        dto.setProdimagepaths(imagePathstoString);

        return dto;
    }

    //================================================================================================
    //상품 목록을 불러오는 서비스
    //================================================================================================
    public List<ProductDto> getProductList(String prodtype,String prodname,List<String>prodtags){

        List<Product> allProducts = productRepository.findAll(ProductSpecifications.productContainsAllKeywordsAndTag(prodtype,prodname,prodtags));

        List<ProductDto> returnList = new ArrayList<ProductDto>();
        ProductDto dto = null;

        //entity => dto
        for(int i=0;i<allProducts.size();i++){

            dto = new ProductDto();
            dto.setProdcode(allProducts.get(i).getProdcode());
            dto.setProdauthor(allProducts.get(i).getProdauthor());
            dto.setProdtype(allProducts.get(i).getProdtype());
            dto.setProdname(allProducts.get(i).getProdname());
            dto.setProdtime(allProducts.get(i).getProdtime());
            dto.setProdcontext(allProducts.get(i).getProdcontext());
            dto.setProdtype(allProducts.get(i).getProdtype());
            dto.setProdtags(allProducts.get(i).getProdtags());
            dto.setProdprice(allProducts.get(i).getProdprice());

            //경로와 이미지이름을 합쳐서 dto에 저장
            List<String> imagePaths = new ArrayList<String>();
            String[] imagePathstoString = null;
            String[] entityimageNames = allProducts.get(i).getProdimages();

            for(int j=0; j<entityimageNames.length;j++){

                imagePaths.add(allProducts.get(i).getProdpath()+File.separator+entityimageNames[j]);

            }
            imagePathstoString = imagePaths.toArray(new String[0]);
            dto.setProdimagepaths(imagePathstoString);



            returnList.add(dto);
        }

        return returnList;
    }


    //================================================================
    //키워드를 등록하는 서비스
    //================================================================
    public void setKeyword(ProductKeywordDto dto) {
        ProductKeyword productKeyword = new ProductKeyword();

        productKeyword.setKeywno(null);
        productKeyword.setKeywname(dto.getKeywname());
        productKeyword.setKeywtext(dto.getKeywtext());

        productKeywordRepository.save(productKeyword);
    }

    //상품을 등록하는 서비스
    public void setProduct(ProductDto dto, MultipartFile[] files) throws IOException {
        path = setOsPath();

        String imagepath = path+File.separator+dto.getProdtype()+File.separator+ UUID.randomUUID();

        //만약 폴더가 없으면 생성한다.
        File dir = new File(imagepath);
        if(!dir.exists())
            dir.mkdirs();


        MultipartFile[] images = files;

        //DB 저장용 toString 으로 변환
        List<String> imageNamelist = new ArrayList<>();
        String[] imageNametoString;

        for(MultipartFile img : images){
            String imgName = img.getOriginalFilename();

            //파일을 실제 서버의 디렉토리에 저장
            File fileobj = new File(imagepath,imgName);
            img.transferTo(fileobj);

            imageNamelist.add(imgName);

        }
        imageNametoString = imageNamelist.toArray(new String[0]);

        dto.setProdimages(imageNametoString);
        dto.setProdpath(imagepath);

        //dto => entity
        Product product = Product.builder()
                .prodcode(null)
                .prodauthor(dto.getProdauthor())
                .prodname(dto.getProdname())
                .prodtype(dto.getProdtype())
                .prodcontext(dto.getProdcontext())
                .prodtime(LocalDateTime.now())
                .prodpath(dto.getProdpath())
                .prodimages(dto.getProdimages())
                .prodtags(dto.getProdtags())
                .prodprice(dto.getProdprice())

                .build();

        productRepository.save(product);
    }

    //================================================================
    //키워드 설명 검색용 키워드를 불러오는 서비스
    //================================================================
    public List<ProductKeywordDto> getKeywordList() {
        List<ProductKeyword> productKeyword = productKeywordRepository.findByProductKeywordLists();

        List<ProductKeywordDto> returnList = new ArrayList<ProductKeywordDto>();
        ProductKeywordDto dto = null;

        for(int i=0;i<productKeyword.size();i++){
            //entity => dto
            dto = new ProductKeywordDto();
            dto.setKeywno(productKeyword.get(i).getKeywno());
            dto.setKeywname(productKeyword.get(i).getKeywname());
            dto.setKeywtext(productKeyword.get(i).getKeywtext());

            returnList.add(dto);
        }
        return returnList;
    }

    public void deleteKeyword(int no) {

        productKeywordRepository.deleteById(no);

    }

    //================================================================
    //상품을 삭제하는 서비스
    //================================================================
    public void deleteProduct(Long no) {

        Optional<Product> optionalProduct = productRepository.findById(no);
        Product product = optionalProduct.get();

        String imagePath = product.getProdpath();

        //경로에 파일이 있으면 삭제
        if(imagePath!=null) {
            File dir = new File(imagePath);
            if (dir.exists()) {
                File files[] = dir.listFiles();
                for (File file : files) {
                    file.delete();
                }
                dir.delete();
            }
        }



        productRepository.deleteById(no);

    }

    //================================================================
    //키워드 수정 서비스
    //================================================================
    public void updateKeyword(ProductKeywordDto dto) {

        ProductKeyword productKeyword = ProductKeyword.builder()
                .keywno(dto.getKeywno())
                .keywname(dto.getKeywname())
                .keywtext(dto.getKeywtext())
                .build();

        productKeywordRepository.save(productKeyword);

    }

    //================================================================
    //OS 저장 경로 지정 서비스
    //================================================================
    public String setOsPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String OS_PATH;

        if (osName.contains("win")) {
            OS_PATH = "c:\\etc\\products";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            OS_PATH = "/etc/shoptest/products";
        } else {
            // 다른 운영 체제의 경우 처리
            OS_PATH = "/etc/products"; // 기본값으로 리눅스 경로 사용
        }

        return OS_PATH;
    }

    //================================================================
    //장바구니에 상품목록을 불러오는 서비스
    //================================================================
    public List<CartDto> getProductCartList(String username){

        List<CartDto_interface> allProducts = cartRepository.findByCartLists(username);
        List<CartDto> returnList = new ArrayList<CartDto>();
        CartDto dto = null;

        for(int i=0;i<allProducts.size();i++){

            dto = new CartDto();
            dto.setCartno(allProducts.get(i).getcartno());
            dto.setProdcode(allProducts.get(i).getprodcode());
            dto.setProdauthor(allProducts.get(i).getprodauthor());
            dto.setProdtype(allProducts.get(i).getprodtype());
            dto.setProdname(allProducts.get(i).getprodname());
            dto.setProdtime(allProducts.get(i).getprodtime());
            dto.setProdcontext(allProducts.get(i).getprodcontext());
            dto.setProdtype(allProducts.get(i).getprodtype());
//            dto.setProdtags(allProducts.get(i).getProdtags());
            dto.setProdprice(allProducts.get(i).getprodprice());
            dto.setCartcount(allProducts.get(i).getcartcount());


            //경로와 이미지이름을 합쳐서 dto에 저장

            //cartDtointerface에서 받아오는 요소는 자동으로 , 단위 컨버트가 안되기 때문에 여기서 직접 , 단위로 잘라 변환한다.
            String imgNames = allProducts.get(i).getprodimages();
            String[] cutImgNames = imgNames.split(",");

            List<String> imagePaths = new ArrayList<String>();
            String[] imagePathstoString = null;
            String[] entityimageNames = cutImgNames;

            for(int j=0; j<entityimageNames.length;j++){

                imagePaths.add(allProducts.get(i).getprodpath()+File.separator+entityimageNames[j]);

            }
            imagePathstoString = imagePaths.toArray(new String[0]);
            dto.setProdimagepaths(imagePathstoString);



            returnList.add(dto);

        }

        return returnList;
    }


    //================================================================
    //장바구니에 상품을 저장하는 서비스
    //================================================================
    public void setProductCart(Long prodcode, String username) {
        Product product = new Product();
        User user = new User();

        product.setProdcode(prodcode);
        user.setUsername(username);

        Cart cart = Cart.builder()
                .cartno(null)
                .product(product)
                .user(user)
                .cartcount(1)
                .cartintime(LocalDateTime.now())
                .build();

        cartRepository.save(cart);

    }

    //================================================================
    //장바구니에 상품을 제거하는 서비스
    //================================================================
    public void removeProductCart(Long prodcode, String username) {

        List<Cart> cart = cartRepository.findByCartProd(prodcode , username);

        cartRepository.deleteAll(cart);
    }

    //================================================================
    //장바구니에 상품 가격 함계를 구하는 서비스
    //================================================================
    public Long cartSumOfPrice(String username) {

        Long price = cartRepository.sumByCartPrice(username);

        return price;
    }

    //================================================================
    //장바구니에 상품 수량을 저장하는 서비스
    //================================================================
    public void setCartCount(Long cartno, int count) {

        Optional<Cart> cart = cartRepository.findById(cartno);

        cart.ifPresent(item -> {

            item.setCartcount(count);
            cartRepository.save(item);

        });

    }

    //================================================================
    //장바구니 목록 모두 제거 하는 서비스
    //================================================================
    public void removeCartDB(String username) {

        List<Cart> cart = cartRepository.findByUserCartList(username);

        cartRepository.deleteAll(cart);

    }

    //================================================================
    //장바구니 목록에서 하나의 항목을 찾는 서비스
    //================================================================
    public CartDto getProductCartOne(String username, Long prodcode) {

        List<CartDto_interface> result = cartRepository.findByCartProdOne(prodcode, username);

        //장바구니에 해당 상품이 있다면
        if(!result.isEmpty()){

            CartDto dto = null;

            dto = new CartDto();
            dto.setCartno(result.get(0).getcartno());
            dto.setProdcode(result.get(0).getprodcode());
            dto.setProdauthor(result.get(0).getprodauthor());
            dto.setProdtype(result.get(0).getprodtype());
            dto.setProdname(result.get(0).getprodname());
            dto.setProdtime(result.get(0).getprodtime());
            dto.setProdcontext(result.get(0).getprodcontext());
            dto.setProdtype(result.get(0).getprodtype());
//            dto.setProdtags(result.get(0).getProdtags());
            dto.setProdprice(result.get(0).getprodprice());
            dto.setCartcount(result.get(0).getcartcount());


            //경로와 이미지이름을 합쳐서 dto에 저장

            //cartDtointerface에서 받아오는 요소는 자동으로 , 단위 컨버트가 안되기 때문에 여기서 직접 , 단위로 잘라 변환한다.
            String imgNames = result.get(0).getprodimages();
            String[] cutImgNames = imgNames.split(",");

            List<String> imagePaths = new ArrayList<String>();
            String[] imagePathstoString = null;
            String[] entityimageNames = cutImgNames;

            for(int j=0; j<entityimageNames.length;j++){

                imagePaths.add(result.get(0).getprodpath()+File.separator+entityimageNames[j]);

            }
            imagePathstoString = imagePaths.toArray(new String[0]);
            dto.setProdimagepaths(imagePathstoString);



            return dto;

        //장바구니에 해당 상품이 없다면
        }else {

            return null;

        }



    }
}
