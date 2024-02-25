package com.myshop.admin.product;

import com.myshop.common.entity.Category;
import com.myshop.common.entity.Product;
import com.myshop.common.exception.CategoryNotFoundException;
import com.myshop.common.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 3;
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);

        if (keyword != null) {
            return productRepository.search(keyword, pageable);
        }


        return productRepository.findAll(pageable);
    }

    public Product save(Product product) {
        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            product.setAlias(product.getName().replaceAll(" ", "--"));
        } else {
            product.setAlias(product.getAlias().replaceAll(" ", "--"));
        }
        return productRepository.save(product);
    }

    public Product savePrice(Product productInForm) throws ProductNotFoundException {
        Product productInDB = get(productInForm.getId());
        productInDB.setPrice(productInForm.getPrice());
        productInDB.setListPrice(productInForm.getListPrice());
        productInDB.setDiscountPercent(productInForm.getDiscountPercent());

        return productRepository.save(productInDB);
    }


    public Product get(Integer id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Couldn't find any product with id = " + id));
    }


    public void updateStatus(Integer productId, boolean status) {

        productRepository.updateStatus(productId, status);
    }

    public void deleteProduct(Integer id) throws ProductNotFoundException {
        get(id);
        productRepository.deleteById(id);
    }


    public String checkUniqueNameAndAlias(Integer id, String name, String alias) {
        Product productByName = productRepository.findByName(name);
        Product productByNameButByAlias = productRepository.findByAlias(name);
        if(productByNameButByAlias!= null)                 return "duplicatedAliasAndName";

        if (productByName != null) {

            if (productByName.getId() != id) {
                return "duplicatedName";
            }
            if(alias.isEmpty()) {
                return "OK";
            }
            Product productByAlias = productRepository.findByAlias(alias);
            if(productByAlias !=null && productByAlias.getId()!= id) {
                return "duplicatedAlias";
            }
        }else {
            Product productByAlias = productRepository.findByAlias(alias);
            if(productByAlias != null && productByAlias.getId() !=id ) {
                return "duplicatedAlias";
            }
        }


        return "OK";
    }


}
