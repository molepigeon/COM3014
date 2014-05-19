/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import beans.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Stores a single User object.
 * 
 * @author Michael
 */
@Service
@Scope("request")
public class UserService {
    private static User user;
    public static User getUser(){
        return user;
    }
    
    public static void setUser(User param){
        user = param;
    }
}
