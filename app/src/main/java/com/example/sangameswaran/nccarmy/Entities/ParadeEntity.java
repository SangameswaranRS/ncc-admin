package com.example.sangameswaran.nccarmy.Entities;

/**
 * Created by Sangameswaran on 12-05-2017.
 */

public class ParadeEntity {
    AttendanceEntity cadet;
    String attendance;

    public ParadeEntity()
    {}

    public ParadeEntity(AttendanceEntity cadet, String attendance) {
        this.cadet = cadet;
        this.attendance = attendance;
    }

    public AttendanceEntity getCadet() {
        return cadet;
    }

    public void setCadet(AttendanceEntity cadet) {
        this.cadet = cadet;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
