/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spectator;
import ControlCentre.*;
import GeneralRepository.*;
import Paddock.*;

/**
 *
 * @author cristianacarvalho
 */
public class Spectator extends Thread{
    
    private SpectatorStates state;
    private IControlCentre_Spectator ccSpectator;
    private IPaddock_Spectator padSpectator;
    private GeneralRepository gr;
    
    private int spectatorID;
    
    public Spectator(IControlCentre_Spectator ccSpectator, IPaddock_Spectator padSpectator, int spectatorID, GeneralRepository gr){
        this.ccSpectator = ccSpectator;
        this.padSpectator = padSpectator;
        this.gr = gr;
        this.spectatorID = spectatorID;
    }
    
    @Override
    public void run() {
        System.out.print("\nO espectador " + spectatorID + " está à espera da próxima corrida.");
        state = SpectatorStates.WAITING_FOR_A_RACE_TO_START;
        waitForNextRace();
    }
    
    public void waitForNextRace(){
        ccSpectator.waitForTheNextRace(spectatorID);
        goCheckHorses();
    }
    
    public void goCheckHorses(){
        state = SpectatorStates.APPRAISING_THE_HORSES;
        padSpectator.goCheckHorses(spectatorID);
        //placeABet();
        
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
