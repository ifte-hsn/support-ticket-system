package com.helloshishir.support.users;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotEmpty(message = "First name can not be empty")
    @Size(min = 3, message = "Minimum 3 characters required")
    @Column(name = "first_name")
    String firstName;

    @NotEmpty(message = "Last name can not be empty")
    @Size(min = 3, message = "Minimum 3 characters required")
    @Column(name = "last_name")
    String lastName;

    @Email
    @NotEmpty(message = "Email can not be empty")
    @Column(name = "email", unique = true)
    String email;

    @NotEmpty(message = "Username is required")
    @Size(min = 3, message = "Minimum 5 characters required")
    @Column(name = "username", unique = true)
    String username;


    @Column(name = "password")
    String password;

    @NotEmpty(message = "Phone no required.")
    @Column(name = "phone")
    String phone;

    @Column(name = "address")
    String address;

    @Column(name = "active")
    Boolean isActive;

    @Column(name = "photo")
    String photo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                ", photo='" + photo + '\'' +
                '}';
    }
}
