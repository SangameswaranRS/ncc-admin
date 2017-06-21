package com.example.sangameswaran.nccarmy.Entities;

/**
 * Created by Sangameswaran on 10-05-2017.
 */

public class AdminEntity {
    String regimental_number;
    String password;
    String user_name;
    String security_question;
    String security_answer;
    String login_flag;
    String blocked_status;
    String isAdmin;
    String isCadet;

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIsCadet() {
        return isCadet;
    }

    public void setIsCadet(String isCadet) {
        this.isCadet = isCadet;
    }

    public String getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(String isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    String isSuperAdmin;


    public  AdminEntity()
    {}
    public AdminEntity(String regimental_number, String password, String user_name, String security_question, String security_answer, String login_flag,String blocked_status) {
        this.regimental_number = regimental_number;
        this.password = password;
        this.user_name = user_name;
        this.security_question = security_question;
        this.security_answer = security_answer;
        this.login_flag = login_flag;
        this.blocked_status=blocked_status;
    }

    public String getBlocked_status() {
        return blocked_status;
    }

    public void setBlocked_status(String blocked_status) {
        this.blocked_status = blocked_status;
    }

    public String getRegimental_number() {
        return regimental_number;
    }

    public void setRegimental_number(String regimental_number) {
        this.regimental_number = regimental_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }

    public String getLogin_flag() {
        return login_flag;
    }

    public void setLogin_flag(String login_flag) {
        this.login_flag = login_flag;
    }
}
