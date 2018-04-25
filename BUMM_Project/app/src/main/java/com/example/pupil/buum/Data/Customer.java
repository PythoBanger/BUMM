/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pupil.buum.Data;

import java.util.Date;

public class Customer {
  private String username, password, firstName, lastName, address, email, location, status, role;
  private Date birthdate;
  private int zipcode;

    public Customer(String username, String password, String firstName, String lastName, String address, String email, String location, String status, String role, Date birthdate, int zipcode) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.location = location;
        this.status = status;
        this.role = role;
        this.birthdate = birthdate;
        this.zipcode = zipcode;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Customer{" + "username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", email=" + email + ", location=" + location + ", status=" + status + ", role=" + role + ", birthdate=" + birthdate + ", zipcode=" + zipcode + '}';
    }
  
  
    
}
