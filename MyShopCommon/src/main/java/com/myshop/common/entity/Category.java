package com.myshop.common.entity;

import com.myshop.common.Constants;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128, nullable = false, unique = true)
    private String name;
    @Column(length = 128, nullable = false, unique = true)
    private String alias;
    @Column(length = 64, nullable = false)
    private String image;
    private boolean enabled;
    @OneToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @OrderBy(value = "name asc")
    private Set<Category> children = new HashSet<>();

    @Column(name = "all_parent_ids",length = 256)
    private String allParentIDs;


    public Category() {
    }

    public Category(Integer id) {
        this.id = id;
    }

    public Category(String name, String alias, String image) {
        this.name = name;
        this.alias = alias;
        this.image = image;
    }

    public static Category CopyFull(Category category) {
        Category copyCate = new Category();
        copyCate.setId(category.getId());
        copyCate.setName(category.getName());
        copyCate.setAlias(category.getAlias());
        copyCate.setImage(category.getImage());
        copyCate.setEnabled(category.isEnabled());
        copyCate.setParent(category.getParent());
        copyCate.setChildren(category.getChildren() );
        copyCate.setHasChildren(category.getChildren().size() >0 );
        return copyCate;
    }
    public static Category CopyFull(Category category,String name) {
        Category copyCate = CopyFull(category);
        copyCate.setName(name);
        return copyCate;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    @Transient
    public String getImagePath() {
        if(image==null) return "/images/no-image.jpg";
        return Constants.AWS_BASE_URI +"/categories-photo/"+id + "/" + image;
    }

    @Transient
    private boolean hasChildren;

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getAllParentIDs() {
        return allParentIDs;
    }

    public void setAllParentIDs(String allParentIDs) {
        this.allParentIDs = allParentIDs;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", imagePath='" + getImagePath() + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
