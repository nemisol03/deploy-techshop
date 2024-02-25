package com.myshop.admin.category;

public class PageCategoryInfo {

    private int totalPages;
    private long totalElements;
    public PageCategoryInfo() {

    }

    public PageCategoryInfo(int totalPages, int totalElements) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
