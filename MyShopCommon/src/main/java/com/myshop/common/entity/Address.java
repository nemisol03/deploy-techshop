package com.myshop.common.entity;


import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name",length = 60)
    private String firstName;
    @Column(name = "last_name",length = 60)

    private String lastName;
    @Column(name = "phone_number")

    private String phoneNumber;
    private String address;

    @Column(name = "default_address")

    private boolean defaultForShipping;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address() {

    }

    public Address(Integer id, String firstName, String lastName, String phoneNumber, String address, boolean defaultForShipping, Customer customer) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.defaultForShipping = defaultForShipping;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public boolean isDefaultForShipping() {
        return defaultForShipping;
    }

    public void setDefaultForShipping(boolean defaultForShipping) {
        this.defaultForShipping = defaultForShipping;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        String fullAddress = firstName;
        if(lastName != null && !lastName.isEmpty()) fullAddress += " "+ lastName ;
        if(!address.isEmpty()) fullAddress+= ", " + address;
        if(!phoneNumber.isEmpty()) fullAddress+= " .Phone number: " + phoneNumber;


        return fullAddress;
    }
}
