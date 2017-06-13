package com.example.sangameswaran.nccarmy.Entities;

/**
 * Created by Sangameswaran on 13-05-2017.
 */

public class AttendanceReportEntity {
    String batch, eme_present_count, eme_absent_count, eng_present_count, eng_absent_count, sig_present_count, sig_absent_count, overall_present_count, overall_absent_count,percentage_of_present;

    public AttendanceReportEntity()
    {

    }

    public AttendanceReportEntity(String batch, String EMEpresentCount, String EMEAbsentCount, String ENGpresentCount, String ENGabsentCount, String SIGpresentCount, String SIGabsentCount, String overallPresentCount, String overallAbsentCount, String percentage_of_present) {
        this.batch = batch;
        this.eme_present_count = EMEpresentCount;
        this.eme_absent_count = EMEAbsentCount;
        this.eng_present_count = ENGpresentCount;
        this.eng_absent_count = ENGabsentCount;
        this.sig_present_count = SIGpresentCount;
        this.sig_absent_count = SIGabsentCount;
        this.overall_present_count = overallPresentCount;
        this.overall_absent_count = overallAbsentCount;
        this.percentage_of_present=percentage_of_present;
    }

    public String getPercentage_of_present() {
        return percentage_of_present;
    }

    public void setPercentage_of_present(String percentage_of_present) {
        this.percentage_of_present = percentage_of_present;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getEMEpresentCount() {
        return eme_present_count;
    }

    public void setEMEpresentCount(String eMEpresentCount) {
        this.eme_present_count = eMEpresentCount;
    }

    public String getEMEAbsentCount() {
        return eme_absent_count;
    }

    public void setEMEAbsentCount(String EMEAbsentCount) {
        this.eme_absent_count = EMEAbsentCount;
    }

    public String getENGpresentCount() {
        return eng_present_count;
    }

    public void setENGpresentCount(String ENGpresentCount) {
        this.eng_present_count = ENGpresentCount;
    }

    public String getENGabsentCount() {
        return eng_absent_count;
    }

    public void setENGabsentCount(String ENGabsentCount) {
        this.eng_absent_count = ENGabsentCount;
    }

    public String getSIGpresentCount() {
        return sig_present_count;
    }

    public void setSIGpresentCount(String SIGpresentCount) {
        this.sig_present_count = SIGpresentCount;
    }

    public String getSIGabsentCount() {
        return sig_absent_count;
    }

    public void setSIGabsentCount(String SIGabsentCount) {
        this.sig_absent_count = SIGabsentCount;
    }

    public String getOverall_present_count() {
        return overall_present_count;
    }

    public void setOverall_present_count(String overall_present_count) {
        this.overall_present_count = overall_present_count;
    }

    public String getOverall_absent_count() {
        return overall_absent_count;
    }

    public void setOverall_absent_count(String overall_absent_count) {
        this.overall_absent_count = overall_absent_count;
    }
}
