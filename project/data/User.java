package com.example.schueler.project.data;

import java.io.Serializable;
import java.util.Arrays;


public class User implements Serializable{


    private String username, password, firstName, lastName, email, location, address, role,status;
    private LocalDate birthdate;
    private int zipCode;
    private byte[] imageData;


    public User() {
    }

    public User(String username,String password){
        this.username=username;
        this.password=password;

    }
    public User(String username, String password, String firstName, String lastName, String email, String location, String address, String role, LocalDate birthdate, int zipCode,String status) {
        this(username,  password,  firstName,  lastName,  email,  location,  address,  role,  birthdate,  zipCode,null,status);
    }

    public User(String username, String password, String firstName, String lastName, String email, String location, String address, String role, LocalDate birthdate, int zipCode, byte[] imageData,String status) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.location = location;
        this.address = address;
        this.role = role;
        this.birthdate = birthdate;
        this.zipCode = zipCode;
        this.imageData = imageData;
        this.status=status;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }


    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", birthdate=" + birthdate +
                ", zipCode=" + zipCode +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }
}
