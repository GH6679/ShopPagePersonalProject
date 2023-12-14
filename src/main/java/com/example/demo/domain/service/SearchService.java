package com.example.demo.domain.service;

import com.example.demo.domain.dto.ProductDto;
import com.example.demo.domain.entity.Product;
import com.example.demo.domain.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SearchService { //통합 검색 서비스


    @Autowired
    private ProductRepository productRepository;

    //통합 검색
    public List<ProductDto> search_all(String key) {

        List<Product> searchProduct = productRepository.findBySearchProduct(key);

        List<ProductDto> returnList = new ArrayList<ProductDto>();
        ProductDto dto = null;

        //entity => dto
        for(int i=0;i<searchProduct.size();i++){

            dto = new ProductDto();
            dto.setProdcode(searchProduct.get(i).getProdcode());
            dto.setProdauthor(searchProduct.get(i).getProdauthor());
            dto.setProdtype(searchProduct.get(i).getProdtype());
            dto.setProdname(searchProduct.get(i).getProdname());
            dto.setProdtime(searchProduct.get(i).getProdtime());
            dto.setProdcontext(searchProduct.get(i).getProdcontext());
            dto.setProdtype(searchProduct.get(i).getProdtype());
            dto.setProdtags(searchProduct.get(i).getProdtags());
            dto.setProdprice(searchProduct.get(i).getProdprice());

            //경로와 이미지이름을 합쳐서 dto에 저장
            List<String> imagePaths = new ArrayList<String>();
            String[] imagePathstoString = null;
            String[] entityimageNames = searchProduct.get(i).getProdimages();

            for(int j=0; j<entityimageNames.length;j++){

                imagePaths.add(searchProduct.get(i).getProdpath()+ File.separator+entityimageNames[j]);

            }
            imagePathstoString = imagePaths.toArray(new String[0]);
            dto.setProdimagepaths(imagePathstoString);



            returnList.add(dto);
        }

        return returnList;
    }


    public List<ProductDto> search_tag(String key) {
        List<Product> searchProduct = productRepository.findBySearchTag(key);

        List<ProductDto> returnList = new ArrayList<ProductDto>();
        ProductDto dto = null;

        //entity => dto
        for(int i=0;i<searchProduct.size();i++){

            dto = new ProductDto();
            dto.setProdcode(searchProduct.get(i).getProdcode());
            dto.setProdauthor(searchProduct.get(i).getProdauthor());
            dto.setProdtype(searchProduct.get(i).getProdtype());
            dto.setProdname(searchProduct.get(i).getProdname());
            dto.setProdtime(searchProduct.get(i).getProdtime());
            dto.setProdcontext(searchProduct.get(i).getProdcontext());
            dto.setProdtype(searchProduct.get(i).getProdtype());
            dto.setProdtags(searchProduct.get(i).getProdtags());
            dto.setProdprice(searchProduct.get(i).getProdprice());

            //경로와 이미지이름을 합쳐서 dto에 저장
            List<String> imagePaths = new ArrayList<String>();
            String[] imagePathstoString = null;
            String[] entityimageNames = searchProduct.get(i).getProdimages();

            for(int j=0; j<entityimageNames.length;j++){

                imagePaths.add(searchProduct.get(i).getProdpath()+ File.separator+entityimageNames[j]);

            }
            imagePathstoString = imagePaths.toArray(new String[0]);
            dto.setProdimagepaths(imagePathstoString);



            returnList.add(dto);
        }

        return returnList;


    }
}
