package com.myshop.site.product;


import com.myshop.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product,Integer> {

    @Query("select p from Product p where p.enabled = true and p.brand.name = ?1 order by p.name asc")
    List<Product> listByBrand(String brandName);

    @Query("select p from Product p where p.enabled = true and (p.category.name = ?1 or p.category.allParentIDs like %?2%) order by p.name asc")
    List<Product> listByCategory(String categoryName,String categoryMatchId);

    @Query("select p from Product p where p.enabled =true and (p.category.id = ?1 or p.category.allParentIDs like %?2%) order by p.name asc")
    Page<Product> listByCategory(Integer categoryId, String categoryMatchId,Pageable pageable) ;

    @Query("select p from Product p where p.enabled = true and p.category.id=?1 and p.id <> ?2")
    List<Product> listRelatedProducts(Integer categoryId,Integer productId);


    //full text search
    @Query(value = "select * from products where enabled = true and match(name,short_description,full_description) against(?1)",nativeQuery = true)
    Page<Product> search(String keyword,Pageable pageable);

}
