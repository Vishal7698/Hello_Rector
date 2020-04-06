package com.example.vishal.afinal.dto;

public class FeeDetailDto {
    private String feeid;
    private String submitedfee;
    private String studentid;
    private String submitedon;

    public String getSubmitedon() {
        return submitedon;
    }

    public void setSubmitedon(String submitedon) {
        this.submitedon = submitedon;
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public String getSubmitedfee() {
        return submitedfee;
    }

    public void setSubmitedfee(String submitedfee) {
        this.submitedfee = submitedfee;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
}
