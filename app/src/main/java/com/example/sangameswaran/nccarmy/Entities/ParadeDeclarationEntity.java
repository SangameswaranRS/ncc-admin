package com.example.sangameswaran.nccarmy.Entities;

/**
 * Created by Sangameswaran on 29-06-2017.
 */

public class ParadeDeclarationEntity  {
    String dress_code,date,time,special_instructions;
    double latitude;
    double longitude;

    public String getDress_code() {
        return dress_code;
    }

    public void setDress_code(String dress_code) {
        this.dress_code = dress_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpecial_instructions() {
        return special_instructions;
    }

    public void setSpecial_instructions(String special_instructions) {
        this.special_instructions = special_instructions;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
