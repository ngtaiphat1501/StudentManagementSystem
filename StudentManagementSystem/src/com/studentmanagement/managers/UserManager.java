/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.studentmanagement.managers;

import com.studentmanagement.models.User;
import com.studentmanagement.services.FileService;
//import com.studentmanagement.services.FileServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class UserManager {
    
    private List<User> users;
    private User curentUser;
    private FileService fileService;

    public UserManager(){
        this.users = new ArrayList<>();
        this.fileService = new FileServiceImpl();
        loadUsersFromFile();
    }
    
    
    
    
    
    
    
    
}
