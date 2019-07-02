package com.ikspl.hps.Models;

import java.util.Date;

public class HomeWorkModel {

    String StuID;
    String HomeWorkDetails;
    Date uploadDate;
    Integer ClassID;

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getStuID() {
        return StuID;
    }

    public void setStuID(String stuID) {
        StuID = stuID;
    }

    public String getHomeWorkDetails() {
        return HomeWorkDetails;
    }

    public void setHomeWorkDetails(String homeWorkDetails) {
        HomeWorkDetails = homeWorkDetails;
    }


    public Integer getClassID() {
        return ClassID;
    }

    public void setClassID(Integer classID) {
        ClassID = classID;
    }
}
