package com.example.sangameswaran.nccarmy.Entities;

/**
 * Created by Sangameswaran on 03-07-2017.
 */

public class MessageEntity {
    private String message;
    private String sender;
    private String time;
    public MessageEntity(){}
    public MessageEntity(String message, String sender, String time) {
        this.message = message;
        this.sender = sender;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
