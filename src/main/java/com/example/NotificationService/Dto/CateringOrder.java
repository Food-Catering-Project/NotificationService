package com.example.NotificationService.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CateringOrder {

    private Long cateringOrderid;

    private int noOfPerson;
    private String email;
    private LocalDate date;
    private String phoneNo;

    public Long getCateringOrderid() {
        return cateringOrderid;
    }

    public void setCateringOrderid(Long cateringOrderid) {
        this.cateringOrderid = cateringOrderid;
    }

    public int getNoOfPerson() {
        return noOfPerson;
    }

    public void setNoOfPerson(int noOfPerson) {
        this.noOfPerson = noOfPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}