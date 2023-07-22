package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<String, List<User>> groupUserMap;
    private HashMap<String, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<String, User> adminMap;
    private HashMap<String, User> userMap;

    private HashMap<Integer, Message> messageMap;

    private HashMap<String , Group> groupName;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<String, List<Message>>();
        this.groupUserMap = new HashMap<String, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<String, User>();
        this.userMap = new HashMap<>();
        this.messageMap = new HashMap<>();
        this.groupName=new HashMap<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(User user) {
        if(userMap.containsKey(user.getMobile())){
            throw new RuntimeException("User already exists");
        }
        userMap.put(user.getMobile(), user);
//        System.out.println(userMap.get(user.getMobile()));
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {

        int numPart = users.size();

        Group group = new Group();

        if(numPart > 2){

            customGroupCount++;
            group.setName("Group "+customGroupCount);
            group.setNumberOfParticipants(numPart);

        }else{
            User other = users.get(1);
            group.setName(other.getName());
            group.setNumberOfParticipants(2);
        }

        groupUserMap.put(group.getName(), users);
        adminMap.put(group.getName(),users.get(0));
        groupMessageMap.put(group.getName(), new ArrayList<>());
        groupName.put(group.getName(), group);

        return group;
    }

    public int createMessage(String content) {
        messageId++;

        Message message = new Message(messageId, content);

        messageMap.put(messageId, message);

        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) {
        boolean group_exist = false;
        for(String group1 : groupUserMap.keySet()){
            if (group1.equals(group.getName())){
                group_exist=true;
            }
        }

        if(!group_exist){
            throw new RuntimeException("Group does not exist");
        }

        List<User> curr_group = groupUserMap.get(group.getName());

        boolean is_user = false;
        for(User curr : groupUserMap.get(group.getName())){
            if(curr.getName().equals(sender.getName())) {
                is_user = true;
            }
        }

        if(!is_user){
            throw new RuntimeException("You are not allowed to send message");
        }

        groupMessageMap.get(group.getName()).add(message);

        return groupMessageMap.get(group.getName()).size();
    }

    public String changeAdmin(User approver, User user, Group group) {
        boolean is_admin = false;
        boolean group_there = false;

        for (String string : adminMap.keySet()) {
            if (adminMap.get(string).getName().equals(approver.getName())) {
                is_admin = true;
            }
        }

        for(String string : groupName.keySet()){

            if(string.equals(groupName.get(string).getName())){
                group_there=true;
            }
        }

        if(!group_there){
            throw new RuntimeException("Group does not exist");
        }

        if(!is_admin){
            throw new RuntimeException("Approver does not have rights");
        }

        List<User> curr_list = groupUserMap.get(group.getName());

        boolean user_member = false;

        for(int i = 0; i < curr_list.size(); i++){

            if(curr_list.get(i).getName().equals(user.getName())){
                user_member=true;
            }
        }

        if(!user_member){
            throw new RuntimeException("User is not a participant");
        }

        adminMap.put(group.getName(), user);

        return "SUCCESS";

    }

    public int removeUser(User user) {
        // from the group.
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        return "Okay";
    }
}