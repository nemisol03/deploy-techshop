package com.myshop.admin.product;


import com.myshop.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product,Integer> {

    @Query("update Product set enabled =?2 where id=?1")
    @Modifying
    void updateStatus(Integer id,boolean status);

    @Query("select p from Product p where concat(p.name,' ',p.shortDescription,' ',p.category.name,' ',p.brand.name,' ') like %?1%")
    Page<Product> search(String keyword, Pageable pageable);

    Product findByName(String name);
    Product findByAlias(String alias);
}
