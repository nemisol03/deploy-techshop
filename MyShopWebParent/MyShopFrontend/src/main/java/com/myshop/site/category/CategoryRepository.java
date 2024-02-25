package com.myshop.site.category;

import com.myshop.common.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category,Integer> {

    @Query("select c from Category c where c.enabled = true order by c.name asc")
    List<Category> listAllEnabled();

    Category findByAlias(String alias);
    Category findByName(String name);



}
