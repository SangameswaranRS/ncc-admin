package com.example.sangameswaran.nccarmy.Entities;

/**
 * Created by Sangameswaran on 12-05-2017.
 */

public class AttendanceEntity {

    String regimental_number,name, platoon,rank;

    public AttendanceEntity()
    {

    }


    public AttendanceEntity(String regimental_number, String name, String platoon,String rank)
    {
        this.regimental_number = regimental_number;
        this.name = name;
        this.platoon = platoon;
        this.rank=rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRegimental_number() {
        return regimental_number;
    }

    public void setRegimental_number(String regimental_number) {
        this.regimental_number = regimental_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatoon() {
        return platoon;
    }

    public void setPlatoon(String platoon) {
        this.platoon = platoon;
    }
}
