/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RacingTrack;

import GeneralRepository.GeneralRepository;

/**
 *
 * @author cristianacarvalho
 */
public class RacingTrack implements IRacingTrack_Horses, IRacingTrack_Broker{
    
    private boolean makeAMove;
    
    public RacingTrack(){
        makeAMove = false;
    }
    
    @Override
    public synchronized void proceedToStartLine() {
        
        while (!makeAMove) {
            try {
                System.out.println("\nOs cavalos estão na StartLine.");
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        notifyAll();
         
    }

    @Override
    public synchronized void makeAMove(){ 
    }

    @Override
    public synchronized void hasFinishLineBeenCrossed() {
    }

    

    @Override
    public void startTheRace() {
    }

    
    
    
}
