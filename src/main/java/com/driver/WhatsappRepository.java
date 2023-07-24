package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<String, List<User>> groupUserMap;
    private HashMap<String, List<ToSendMessage>> groupMessageMap;
    private HashMap<String, Group> userGroupMap;
    private HashMap<String, String> adminMap;
    private HashMap<String, User> userMap;
    private HashMap<Integer, Message> messageMap;
    private HashMap<String , Group> groupName;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<String, List<ToSendMessage>>();
        this.groupUserMap = new HashMap<String, List<User>>();
        this.userGroupMap = new HashMap<String, Group>();
        this.adminMap = new HashMap<String, String>();
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
        userMap.put(user.getName(), user);
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
        adminMap.put(users.get(0).getName(), group.getName());
        groupMessageMap.put(group.getName(), new ArrayList<>());
        groupName.put(group.getName(), group);

        for(User user : users){
            userGroupMap.put(user.getName(), group);
        }

        return group;
    }

    public int createMessage(String content) {
        messageId++;

        Message message = new Message(messageId, content);

        messageMap.put(messageId, message);

        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) {

//        if(!userMap.containsKey(sender.getName())){
//            throw new RuntimeException("User does not exist");
//        }

        if(!groupName.containsKey(group.getName())){
            throw new RuntimeException("Group does not exist");
        }

        if(userGroupMap.containsKey(sender.getName())
                && !userGroupMap.get(sender.getName()).getName().equals(group.getName())){
            throw new RuntimeException("You are not allowed to send message");
        }

        ToSendMessage toSendMessage = new ToSendMessage();
        toSendMessage.setUser(sender);
        toSendMessage.setGroup(group);
        toSendMessage.setMessage(message);

        groupMessageMap.get(group.getName()).add(toSendMessage);

        return groupMessageMap.get(group.getName()).size();
    }

    public String changeAdmin(User approver, User user, Group group) {

        if(!groupName.containsKey(group.getName())){
            throw new RuntimeException("Group does not exist");
        }

        if(!adminMap.containsKey(approver.getName())){
            throw new RuntimeException("Approver does not have rights");
        }

        if(userGroupMap.containsKey(user.getName())
                && !userGroupMap.get(user.getName()).getName().equals(group.getName())){
            throw new RuntimeException("User is not a participant");
        }

        adminMap.remove(approver.getName());
        adminMap.put(user.getName(), group.getName());

        return "SUCCESS";

    }

    public int removeUser(User user) {
        if (!userMap.containsKey(user.getName())){
            throw new RuntimeException("User does not exist.");
        }

        if(!userGroupMap.containsKey(user.getName())){
           throw new RuntimeException("Group does not exist");
        }

        if(adminMap.containsKey(user.getName())){
            throw new RuntimeException("User is Admin");
        }

        String userGroupName = userGroupMap.get(user.getName()).getName();

        userGroupMap.remove(user.getName());

        userMap.remove(user.getName());

        List<ToSendMessage> list = groupMessageMap.get(userGroupName);

        List<ToSendMessage> updated = new ArrayList<>();

        for (ToSendMessage toSendMessage : list) {
            if (!toSendMessage.getUser().getName().equals(user.getName())) {
                updated.add(toSendMessage);
            }else{
                messageMap.remove(toSendMessage.getMessage().getId());
            }
        }

        groupMessageMap.put(userGroupName, updated);

        List<User> userList = groupUserMap.get(userGroupName);

        int to_delete = 0;

        for(int i = 0; i < userList.size(); i++){
            User user1 = userList.get(i);
            if(user1.getName().equals(user.getName())){
                to_delete = i;
            }
        }

        userList.remove(to_delete);

        groupUserMap.put(userGroupName, userList);

        return groupMessageMap.get(userGroupName).size()+groupUserMap.get(userGroupName).size() + messageMap.size();
    }

    public String findMessage(Date start, Date end, int k) {
        return "Okay";
    }
}