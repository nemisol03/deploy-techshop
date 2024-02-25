package com.myshop.common.entity;

import com.myshop.common.Constants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 512, nullable = false, unique = true)
    private String name;
    @Column(length = 512, nullable = false, unique = true)

    private String alias;

    @Column(name = "list_price")
    private Float listPrice;

    private Float price;
    @Column(name = "short_description", columnDefinition = "TEXT")

    private String shortDescription;
    @Column(name = "full_description", columnDefinition = "TEXT")

    private String fullDescription;
    @Column(name = "main_image", length = 128, nullable = false)
    private String mainImage;
    @Column(name = "in_stock")
    private boolean inStock;


    private boolean enabled;
    @Column(name = "discount_percent")
    private float discountPercent;
    @ManyToOne
    @JoinColumn(name = "category_id")

    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;


    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "modified_at")
//    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductDetail> details = new HashSet<>();

    public Product(Integer id) {
        this.id = id;
    }

    public void setListPrice(Float listPrice) {
        this.listPrice = listPrice;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Set<ProductImage> getImages() {
        return images;
    }

    public void setImages(Set<ProductImage> images) {
        this.images = images;
    }

//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        modifiedAt = createdAt;
//
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        modifiedAt = LocalDateTime.now();
//
//    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Float getListPrice() {
        return listPrice;
    }

    public void setListPrice(float listPrice) {
        this.listPrice = listPrice;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", category=" + category.getName() +
                ", brand=" + brand.getName() +
                '}';
    }

    @Transient
    public String getMainImagePath() {
        if (mainImage == null) return "/images/no-image.jpg";
        return Constants.AWS_BASE_URI + "/products-photos/" + id + "/" + mainImage;
    }

    @Transient
    public String getShortName() {
        if (name.length() > 70) return name.substring(0, 70).concat("...");
        return name;
    }

    @Transient
    public String getBrandName(){
        return brand.getName();
    }

    @Transient
    public String getCategoryName() {
        return category.getName();
    }

    public void addExtraImage(String name, Product product) {
        images.add(new ProductImage(name, this));
    }

    public void addDetail(String name,String value,Product product) {
        details.add(new ProductDetail(name,value,this));
    }
    public void addDetail(Integer id,String name,String value,Product product) {
        details.add(new ProductDetail(id,name,value,this));
    }

    public Set<ProductDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<ProductDetail> details) {
        this.details = details;
    }

    @Transient
    public String getUpdatedAt() {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd _ HH:mm:ss");
        String timestamp = formatter.format(modifiedAt);
        return timestamp;
    }



    public boolean containImageName(String fileName) {
        Iterator<ProductImage> iterator = images.iterator();
        while (iterator.hasNext()) {
            if(iterator.next().getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }


    @Transient
    public float getPriceWithDiscountPercent() {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        if(discountPercent > 0) {
            return Float.parseFloat(decimalFormat.format(price - price*discountPercent/100));
        }

        return Float.parseFloat(decimalFormat.format(price));
    }




}
