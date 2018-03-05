/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author cristianacarvalho
 */
public class ThreadHorses extends Thread{
    
    public final int id; 
    
    public ThreadHorses(int id){
        this.id = id;
        
    }
}
