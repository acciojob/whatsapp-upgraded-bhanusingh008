package com.driver;

public class toSendMessage {
    private Message message;
    private User sender;
    private Group group;

    public toSendMessage(Message message, User user, Group group) {
        this.message = message;
        this.sender = user;
        this.group = group;

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
}
