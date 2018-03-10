/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Horse;

/**
 *
 * @author cristianacarvalho
 */
public class Horse extends Thread{
    
    
    private HorseStates state;
    
    
    public Horse(){
    
    
    }
    
    
    
    @Override
    public void run() {
        state = HorseStates.AT_THE_STABLE;
        proceedToStable();
    }
    
    public void proceedToStable(){ 
    }
    
    public void proceedToPaddock(){
        state = HorseStates.AT_THE_PADDOCK;
    }
    
    public void proceedToStartLine(){
        state = HorseStates.AT_THE_START_LINE;
    }
    
    public void makeAmoke(){
        state = HorseStates.RUNNING;
    }
    
}
