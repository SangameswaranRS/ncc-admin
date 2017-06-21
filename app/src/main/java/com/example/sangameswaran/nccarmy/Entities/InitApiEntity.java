package com.example.sangameswaran.nccarmy.Entities;

/**
 * Created by Sangameswaran on 21-06-2017.
 */

public class InitApiEntity {
    private String dBAContactNumber;
    private String documentationInchargeContactNumber;
    private String documentationIncharge2ContactNumber;
    private String emeContact;
    private String engContact;
    private String sigContact;
    private AdminEntity user;

    public String getdBAContactNumber() {
        return dBAContactNumber;
    }

    public void setdBAContactNumber(String dBAContactNumber) {
        this.dBAContactNumber = dBAContactNumber;
    }

    public String getDocumentationInchargeContactNumber() {
        return documentationInchargeContactNumber;
    }

    public void setDocumentationInchargeContactNumber(String documentationInchargeContactNumber) {
        this.documentationInchargeContactNumber = documentationInchargeContactNumber;
    }

    public String getDocumentationIncharge2ContactNumber() {
        return documentationIncharge2ContactNumber;
    }

    public void setDocumentationIncharge2ContactNumber(String documentationIncharge2ContactNumber) {
        this.documentationIncharge2ContactNumber = documentationIncharge2ContactNumber;
    }

    public String getEmeContact() {
        return emeContact;
    }

    public void setEmeContact(String emeContact) {
        this.emeContact = emeContact;
    }

    public String getEngContact() {
        return engContact;
    }

    public void setEngContact(String engContact) {
        this.engContact = engContact;
    }

    public String getSigContact() {
        return sigContact;
    }

    public void setSigContact(String sigContact) {
        this.sigContact = sigContact;
    }

    public AdminEntity getUser() {
        return user;
    }

    public void setUser(AdminEntity user) {
        this.user = user;
    }
}
