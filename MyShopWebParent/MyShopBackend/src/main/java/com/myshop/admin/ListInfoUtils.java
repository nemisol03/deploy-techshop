package com.myshop.admin;

import com.myshop.common.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public class ListInfoUtils {

    public static <T> void setInfoList(Model model, int pageNum, String sortField, String sortDir, String keyword, long endCount, Page<T> page, int startCount, List<T> list) {
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("sortField", sortField);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("list", list);
    }
}
