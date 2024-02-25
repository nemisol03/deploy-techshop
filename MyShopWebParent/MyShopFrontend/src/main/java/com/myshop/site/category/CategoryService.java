package com.myshop.site.category;

import com.myshop.common.entity.Category;
import com.myshop.common.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CategoryService {
    @Autowired private CategoryRepository categoryRepository;

    public List<Category> listAllEnabled() {
        return categoryRepository.listAllEnabled();
    }

    public Category getByAlias(String alias) {
        return categoryRepository.findByAlias(alias);}
    public Category getByName(String name) {
        return categoryRepository.findByName(name);}

    public List<Category> getCategoryParents(Category child ) {
        List<Category> listCategoryParents = new ArrayList<>();
        Category parent = child.getParent();
        while(parent!=null) {
            listCategoryParents.add(0,parent);
            parent = parent.getParent();
        }
        listCategoryParents.add(child);
        return listCategoryParents;

    }
    public Category get(Integer id) throws CategoryNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Couldn't find any category with id = " + id));
    }


}
