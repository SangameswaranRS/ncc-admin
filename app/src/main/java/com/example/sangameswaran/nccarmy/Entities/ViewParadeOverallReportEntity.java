package com.example.sangameswaran.nccarmy.Entities;

/**
 * Created by Sangameswaran on 15-05-2017.
 */

public class ViewParadeOverallReportEntity {
    AttendanceReportEntity report;
    String date;

    public  ViewParadeOverallReportEntity()
    {}

    public ViewParadeOverallReportEntity(AttendanceReportEntity report, String date) {
        this.report = report;
        this.date = date;
    }

    public AttendanceReportEntity getReport() {
        return report;
    }

    public void setReport(AttendanceReportEntity report) {
        this.report = report;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
