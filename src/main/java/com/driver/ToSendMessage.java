package com.driver;

import java.util.Date;

public class ToSendMessage {
    private Message message;
    private User sender;
    private Group group;

    private Date date;

    public ToSendMessage() {
    }

    public ToSendMessage(Message message, User user, Group group) {
        this.message = message;
        this.sender = user;
        this.group = group;
        this.date=new Date();

//        System.out.println(message.getContent()+" "+user.getMobile()+" "+group.getName());
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return sender;
    }

    public void setUser(User user) {
        this.sender = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getDate() {
        return date;
    }
}
