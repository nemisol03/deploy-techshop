package com.myshop.admin.brand;

import com.myshop.common.entity.Brand;
import com.myshop.common.exception.BrandNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    public static final int BRAND_PER_PAGE = 3;
    @Autowired private BrandRepository brandRepository;

    public Page<Brand> listByPage(int pageNum,String sortField,String sortDir,String keyword) {
        Sort sort = Sort.by(sortField);
        sort  = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum-1,BRAND_PER_PAGE,sort);
        if(keyword!=null) {
            return brandRepository.search(keyword,pageable);
        }
        return brandRepository.findAll(pageable);



    }
    public Brand save(Brand brand) {
        return brandRepository.save(brand);}
    public Brand get(Integer id) throws BrandNotFoundException {
        return brandRepository.findById(id).orElseThrow(
                ()-> new BrandNotFoundException("Couldn't find any brand with id: " + id)
        );
    }

    public void deleteBrand(Integer id) throws BrandNotFoundException {
        get(id);
        brandRepository.deleteById(id);
    }

    public List<Brand> listAll() {
            return (List<Brand>) brandRepository.findAll();
    }


    public boolean isUniqueBrand(Integer id,String name) {
        Brand brandByName = brandRepository.findByName(name);
        if(brandByName== null) return true;
        if(id==null) {
            return false;
        }else {
            if(brandByName.getId()!=id) {
                return false;
            }
        }

        return true;
    }
}
