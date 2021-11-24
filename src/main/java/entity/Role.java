/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author agolu
 */
public enum Role implements Serializable{
    USER(1),   
    ADMIN(2);
    public final int value;
    
    private Role(int value){
        this.value = value;
    }
}
