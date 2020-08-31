/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.exception;

/**
 *
 * @author nasir
 */
public class UserBlockedException extends Exception{
    //Create user object without error description
    public UserBlockedException(){
        
    }
    
    public UserBlockedException(String errDesc){
        super(errDesc);
    }
}
