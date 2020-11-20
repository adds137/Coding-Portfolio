package com.scrumoftheearth.springbootapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "table_user")
@ApiModel(description = "User Model")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "User Name cannot be blank!")
    @ApiModelProperty(name="userName", required = true)
    private String userName;

    @NotBlank(message = "First Name cannot be blank!")
    @ApiModelProperty(name="firstName", required = true)
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank!")
    @ApiModelProperty(name="lastName", required = true)
    private String lastName;

    @NotBlank(message = "Phone Number cannot be blank!")
    @ApiModelProperty(name="phoneNumber", required = true)
    private String phoneNumber;

    @NotBlank(message = "Home Address cannot be blank!")
    @ApiModelProperty(name="homeAddress", required = true)
    private String homeAddress;

    @JsonIgnore
    @ApiModelProperty(name="saltedHashedPassword", hidden = true)
    private String saltedHashedPassword;

    /* Transient indicates that the fields do not get saved to the database.
    *  As such they are just for mapping the JSON to the User object with validation
    *  The password fields are to be hashed before saving
    *  Note: Validation of Password and Password Confirmation must be handled by the controller.
    * */
    @Transient
    @ApiModelProperty(name="password", required = true, notes = "Field is Transient")
    private String password;

    @Transient
    @ApiModelProperty(name="passwordConfirmation", required = true, notes = "Field is Transient")
    private String passwordConfirmation;

    //CascadeType.ALL - This means if the user is deleted so is the worker
    //We will never actually "delete" the user however this is just good measure
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Worker worker;

    /* https://www.baeldung.com/spring-boot-formatting-json-dates */

    @OneToOne(mappedBy = "Owner", cascade = CascadeType.ALL)
    private Business business;

    @JsonFormat(pattern="yyyy-mm-dd")
    @ApiModelProperty(name="createdAt")
    private Date createdAt;

    @JsonFormat(pattern="yyyy-mm-dd")
    @ApiModelProperty(name="createdAt")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
            this.updatedAt = new Date();
    }

    public User() { }

    public User(String userName, String firstName, String lastName,
                String homeAddress, String phoneNumber, String password, String passwordConfirmation) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getSaltedHashedPassword() {
        return saltedHashedPassword;
    }

    public void setSaltedHashedPassword(String saltedHashedPassword) {
        this.saltedHashedPassword = saltedHashedPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                userName.equals(user.userName) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                phoneNumber.equals(user.phoneNumber) &&
                homeAddress.equals(user.homeAddress) &&
                Objects.equals(saltedHashedPassword, user.saltedHashedPassword) &&
                Objects.equals(password, user.password) &&
                Objects.equals(passwordConfirmation, user.passwordConfirmation) &&
                Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName, phoneNumber, homeAddress, saltedHashedPassword, password, passwordConfirmation, createdAt, updatedAt);
    }
}
