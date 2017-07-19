package com.example.sangameswaran.nccarmy.Entities;

/**
 * Created by Sangameswaran on 19-07-2017.
 */

public class AssignTaskEntity {
    String assigning_user,assigned_user,assigned_timestamp,status,deadline,contact_number,task_requirement;

    public AssignTaskEntity(){}
    public String getAssigning_user() {
        return assigning_user;
    }

    public void setAssigning_user(String assigning_user) {
        this.assigning_user = assigning_user;
    }

    public String getAssigned_user() {
        return assigned_user;
    }

    public void setAssigned_user(String assigned_user) {
        this.assigned_user = assigned_user;
    }

    public String getAssigned_timestamp() {
        return assigned_timestamp;
    }

    public void setAssigned_timestamp(String assigned_timestamp) {
        this.assigned_timestamp = assigned_timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getTask_requirement() {
        return task_requirement;
    }

    public void setTask_requirement(String task_requirement) {
        this.task_requirement = task_requirement;
    }
}
