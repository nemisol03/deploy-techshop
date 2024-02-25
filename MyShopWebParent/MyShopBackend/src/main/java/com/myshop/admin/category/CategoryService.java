package com.myshop.admin.category;

import com.myshop.common.entity.Brand;
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
    public static final int ROOT_CATEGORIES_PER_PAGE=2;
    @Autowired private CategoryRepository categoryRepository;

    public List<Category> listByPage(PageCategoryInfo pageInfo, int pageNum, String sortField, String sortDir, String keyword) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1,ROOT_CATEGORIES_PER_PAGE,sort);
        Page<Category> page = null;

        if(keyword!=null) {
            page = categoryRepository.searchCategory(keyword,pageable);
        }else {
           page = categoryRepository.findRootCategories(pageable);
        }

        List<Category> rootCategories = page.getContent(); //only ROOT_CATEGORIES_PER_PAGE elements
        pageInfo.setTotalPages(page.getTotalPages());
        pageInfo.setTotalElements(page.getTotalElements());

        return listHierarchicalListingPage(rootCategories,sortDir);
    }

    public List<Category> listHierarchicalListingPage(List<Category> rootCategories, String sortDir) {
        List<Category> listHierarchical  = new ArrayList<>();
        for(var category : rootCategories) {
            listHierarchical.add(Category.CopyFull(category));
            addSubCategoriesInPage(listHierarchical,sortSubCategories(category.getChildren(),sortDir),1,sortDir);
        }
        return listHierarchical;
    }

    private void addSubCategoriesInPage(List<Category> listHierarchical, Set<Category> categories, int depth,String sortDir) {
        for(var subCate : categories) {
            String name = getDepthCate(depth) + subCate.getName();
            listHierarchical.add(Category.CopyFull(subCate,name));
            addSubCategoriesInPage(listHierarchical,sortSubCategories(subCate.getChildren(),sortDir),depth+1,sortDir);
        }
    }

    public List<Category> listHierarchicalUseInForm() {
        List<Category> listHierarchical  = new ArrayList<>();
        List<Category> rootCategoriesList = categoryRepository.findRootCategories();
        for(var category : rootCategoriesList) {
            listHierarchical.add(Category.CopyFull(category));
            addSubCategoriesUseInForm(listHierarchical,sortSubCategories(category.getChildren(),"asc"),1);
        }
        return listHierarchical;
    }

    private void addSubCategoriesUseInForm(List<Category> listHierarchical, Set<Category> categories, int depth) {
        for(var subCate : categories) {
            String name = getDepthCate(depth) + subCate.getName();
            listHierarchical.add(Category.CopyFull(subCate,name));
            addSubCategoriesUseInForm(listHierarchical,sortSubCategories(subCate.getChildren(),"asc"),depth+1);
        }
    }

    private String getDepthCate(int depth) {
        StringBuilder prefix = new StringBuilder();
        for(int i =0;i<depth;i++) {
            prefix.append("--");
        }
        return prefix.toString();
    }

    private SortedSet<Category> sortSubCategories(Set<Category> categories,String sortDir) {
        SortedSet<Category> sortedSet = new TreeSet<>(new Comparator<Category>() {
            @Override
            public int compare(Category cate1, Category cate2) {
                if(sortDir.equals("asc")) {
                    return cate1.getName().compareTo(cate2.getName());

                }else {
                    return cate2.getName().compareTo(cate1.getName());

                }
            }
        });
        sortedSet.addAll(categories);
        return sortedSet;
    }


    public Category get(Integer id) throws CategoryNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Couldn't find any category with id = " + id));
    }
    public Category saveCategory(Category category) {
        Category parent = category.getParent();
        if(parent!=null) {
            String allParentIDs = parent.getAllParentIDs() == null ? "-" : parent.getAllParentIDs();
            allParentIDs+= parent.getId() + "-";
            category.setAllParentIDs(allParentIDs );
        }

          return categoryRepository.save(category);
    }


    public void updateStatus(Integer id,boolean status) throws CategoryNotFoundException {
        get(id);
        categoryRepository.updateStatus(id,status);
    }
    public void deleteCategory(Integer id) throws CategoryNotFoundException {
        get(id);
        categoryRepository.deleteById(id);
    }

    public String checkUniqueNameAndAlias(Integer id,String name,String alias) {
        Category categoryByName = categoryRepository.findByName(name);
        if(categoryByName!=null) {
            if(categoryByName.getId()!= id) {
                return "duplicatedName";
            }
            Category categoryByAlias = categoryRepository.findByAlias(alias);
            if(categoryByAlias!=null ) {
                if(categoryByAlias.getId()!=id) {
                    return "duplicatedAlias";
                }
            }
        }else {
            Category categoryByAlias = categoryRepository.findByAlias(alias);
            if(categoryByAlias!=null && categoryByAlias.getId()!=id) {
                return "duplicatedAlias";
            }
        }
            return "OK";
    }


}
