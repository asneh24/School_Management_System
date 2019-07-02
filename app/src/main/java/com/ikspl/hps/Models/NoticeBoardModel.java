package com.ikspl.hps.Models;

import java.util.Date;

public class NoticeBoardModel {

    String StuID;
    String NoticeDetails;
    Date uploadDate;
    Integer ClassID;

    public String getStuID() {
        return StuID;
    }

    public void setStuID(String stuID) {
        StuID = stuID;
    }

    public String getNoticeDetails() {
        return NoticeDetails;
    }

    public void setNoticeDetails(String noticeDetails) {
        NoticeDetails = noticeDetails;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getClassID() {
        return ClassID;
    }

    public void setClassID(Integer classID) {
        ClassID = classID;
    }
}
