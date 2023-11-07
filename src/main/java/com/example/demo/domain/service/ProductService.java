package com.example.demo.domain.service;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.dto.ProductKeywordDto;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.entity.ProductKeyword;
import com.example.demo.domain.repository.ProductKeywordRepository;
import com.example.demo.domain.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    //상품 하나를 불러오는 서비스
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

    //상품 목록을 불러오는 서비스
    public List<ProductDto> getProductList(){

        List<Product> allProducts = productRepository.getByProductLists();

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



    //키워드를 등록하는 서비스
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

                .build();

        productRepository.save(product);
    }

    //키워드 설명 검색용 키워드를 불러오는 서비스
    public List<ProductKeywordDto> getKeywordList() {
        List<ProductKeyword> productKeyword = productKeywordRepository.getByProductKeywordLists();

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

    //상품을 삭제하는 서비스
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

    public void updateKeyword(ProductKeywordDto dto) {

        ProductKeyword productKeyword = ProductKeyword.builder()
                .keywno(dto.getKeywno())
                .keywname(dto.getKeywname())
                .keywtext(dto.getKeywtext())
                .build();

        productKeywordRepository.save(productKeyword);

    }

    public String setOsPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String OS_PATH;

        if (osName.contains("win")) {
            OS_PATH = "c:\\etc\\products";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            OS_PATH = "/etc/products";
        } else {
            // 다른 운영 체제의 경우 처리
            OS_PATH = "/etc/products"; // 기본값으로 리눅스 경로 사용
        }

        return OS_PATH;
    }
}
