package com.myshop.admin.category;

import com.myshop.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category,Integer> {


    @Query("select c from Category c where c.parent.id is null order by c.name")
    List<Category> findRootCategories();

    @Query("select c from Category c where c.parent.id is null")
    Page<Category> findRootCategories(Pageable pageable);



    @Query("select c from Category c where concat(c.id,' ',c.name,' ',c.alias,' ') like %?1%")
    Page<Category> searchCategory(String keyword, Pageable pageable);

    @Query("update Category c set c.enabled = ?2 where c.id=?1")
    @Modifying
    void updateStatus(Integer id, boolean status);

    Category findByName(String name);
    Category findByAlias(String alias);


}
