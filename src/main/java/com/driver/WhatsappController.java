package com.driver;

import java.util.*;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("whatsapp")
public class WhatsappController {

    //Autowire will not work in this case, no need to change this and add autowire
    WhatsappService whatsappService = new WhatsappService();

    @PostMapping("/add-user")
    public String createUser(String name, String mobile) throws Exception {
        return whatsappService.createUser(name, mobile);
    }

    @PostMapping("/add-group")
    public Group createGroup(@RequestBody List<User> users){
        return whatsappService.createGroup(users);
    }

    @PostMapping("/add-message")
    public int createMessage(@RequestParam("content") String content){
        return whatsappService.createMessage(content);
    }
    //
    @PutMapping("/send-message")
    public int sendMessage(@RequestBody Message message, @RequestParam("user") User sender,@RequestParam("group") Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.

        return whatsappService.sendMessage(message, sender, group);
    }
    @PutMapping("/change-admin")
    public String changeAdmin(@RequestBody User approver, User user, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        return whatsappService.changeAdmin(approver, user, group);
    }
//
    @DeleteMapping("/remove-user")
    public int removeUser(User user) throws Exception{
        //This is a bonus problem and does not contains any marks
        //A user belongs to exactly one group
        //If user is not found in any group, throw "User not found" exception
        //If user is found in a group and it is the admin, throw "Cannot remove admin" exception
        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)

        return whatsappService.removeUser(user);
    }
//
    @GetMapping("/find-messages")
    public String findMessage(Date start, Date end, int K) throws Exception{
        //This is a bonus problem and does not contains any marks
        // Find the Kth latest message between start and end (excluding start and end)
        // If the number of messages between given time is less than K, throw "K is greater than the number of messages" exception

        return whatsappService.findMessage(start, end, K);
    }
}
