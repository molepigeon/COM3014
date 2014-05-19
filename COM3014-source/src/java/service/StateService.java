/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import beans.State;

/**
 * Stores a single state object.
 * 
 * @author Michael
 */
public class StateService {
    private static State state;
    
    public static State getState(){
        return state;
    }
    
    public static void setState(State newState){
        state = newState;
    }
}
