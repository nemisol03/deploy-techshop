package com.myshop.site.product;

import com.myshop.common.entity.Category;
import com.myshop.common.entity.Product;
import com.myshop.common.exception.ProductNotFoundException;
import com.myshop.site.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 3;
    public static final int RESULT_PRODUCTS_PER_PAGE = 3;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;


    public List<Product> listAll() {
        return (List<Product>) productRepository.findAll();
    }





    public List<Product> listByBrand(String brandName) {
        return productRepository.listByBrand(brandName);
    }
    public List<Product> listByCategory(String categoryName) {
        Category category = categoryService.getByName(categoryName);
        String categoryMatchId = "-" + category.getId() +"-";

        return productRepository.listByCategory(categoryName,categoryMatchId);
    }

    public Page<Product> listByCategory(int pageNum,Integer categoryId) {
        Pageable pageable = PageRequest.of(pageNum-1,PRODUCTS_PER_PAGE);
        String categoryMatchId = "-" + categoryId + "-";
        return productRepository.listByCategory(categoryId,categoryMatchId,pageable);
    }


    public Product get(Integer id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Couldn't find any product with id = " + id));
    }

    public List<Product> listRelatedProductsByCategory(Integer categoryId,Integer productId) {
        return productRepository.listRelatedProducts(categoryId,productId);
    }

    public Page<Product> search(int pageNum,String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, RESULT_PRODUCTS_PER_PAGE);
        return productRepository.search(keyword,pageable);
    }

}
