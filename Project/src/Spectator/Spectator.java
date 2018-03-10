/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spectator;

/**
 *
 * @author cristianacarvalho
 */
public class Spectator extends Thread{
    
    private SpectatorStates state;
    
    public Spectator(){
    }
    
    @Override
    public void run() {
        state = SpectatorStates.WAITING_FOR_A_RACE_TO_START;
        waitForNextRace();
    }
    
    public void waitForNextRace(){}
    
    public void goCheckHorses(){
        state = SpectatorStates.APPRAISING_THE_HORSES;
    }
    
    public void placeABet(){
        state = SpectatorStates.PLACING_A_BET;
    }
    
    public void goWatchTheRace(){
        state = SpectatorStates.WATCHING_A_RACE;
    }
    
    public void goCollectTheGains(){
        state = SpectatorStates.COLLECTING_THE_GAINS;
    }
    
    public void relaxABit(){
        state = SpectatorStates.CELEBRATING;
    }
    
    
}
