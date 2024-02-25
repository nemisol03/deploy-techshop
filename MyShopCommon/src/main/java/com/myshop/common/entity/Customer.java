package com.myshop.common.entity;

import com.myshop.common.Constants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128,nullable = false,unique = true)
    private String email;
    @Column(length = 64,nullable = false)
    private String password;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean enabled;
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type",length = 10)
    private AuthenticationType authType;


    private String address;
    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name="reset_password_token",length = 30)
    private String resetPasswordToken;



    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Address> addressList = new ArrayList<>();

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();







    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "modified_at")
//    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        modifiedAt = createdAt;
//    }
//    @PreUpdate
//    protected void onUpdate() {
//        modifiedAt = LocalDateTime.now();
//    }

    public Customer() {
    }


    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Customer(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public String getCreatedAtStr() {
        String timestamp = "";
        if(createdAt!=null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");
         timestamp = formatter.format(createdAt);

        }
        return timestamp;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Transient
    public String getImagePath() {
        if(image == null) return "/images/no-image.jpg";
        return Constants.AWS_BASE_URI +"/customers-photo/" + id + "/" + image;
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    public String getModifiedAtStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");
        String timestamp = formatter.format(modifiedAt);
        return timestamp;
    }

    public void setAuthType(AuthenticationType authType) {
        this.authType = authType;
    }
    public AuthenticationType getAuthType() {return authType;}


    @Transient
    public String getFullAddress() {
        String fullAddress = firstName;
        if(lastName != null && !lastName.isEmpty()) fullAddress += " " + lastName;
        if(address!=null && !address.isEmpty()) fullAddress+= ", " + address;
        if(phoneNumber!=null && !phoneNumber.isEmpty()) fullAddress+= " .Phone number: " + phoneNumber;
        return fullAddress;
    }


}
