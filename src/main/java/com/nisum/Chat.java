package com.nisum;

import java.io.Serializable;

public class Chat implements Serializable {
    private String orderid;
    private String timestamp;

    @Override
    public String toString() {
        return "Chat{" +
                "orderid='" + orderid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    private String username;
    private String message;

    public Chat(String orderid, String timestamp, String username, String message) {
        this.orderid = orderid;
        this.timestamp = timestamp;
        this.username = username;
        this.message = message;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chat() {
    }
}
