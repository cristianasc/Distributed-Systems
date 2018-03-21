/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spectator;
import ControlCentre.*;
import GeneralRepository.*;
import Paddock.*;
import BettingCentre.*;
import java.util.Random;

/**
 *
 * @author cristianacarvalho
 */
public class Spectator extends Thread{
    
    private SpectatorStates state;
    private IControlCentre_Spectator ccSpectator;
    private IPaddock_Spectator padSpectator;
    private IBettingCentre_Spectator bcSpectator;
    private GeneralRepository gr;
    private int spectatorID, bestHorse, money, bet,nRaces;
    
    public Spectator(IBettingCentre_Spectator bcSpectator, IControlCentre_Spectator ccSpectator, IPaddock_Spectator padSpectator, int spectatorID, GeneralRepository gr){
        this.ccSpectator = ccSpectator;
        this.padSpectator = padSpectator;
        this.bcSpectator = bcSpectator;
        this.gr = gr;
        this.spectatorID = spectatorID;
        this.money = 5;
        this.bet = 0;
        this.nRaces = gr.getnRaces();
    }
    
    @Override
    public void run() {
        System.out.print("\nO espectador " + spectatorID + " está à espera da próxima corrida.");
        state = SpectatorStates.WAITING_FOR_A_RACE_TO_START;
        ccSpectator.waitForTheNextRace(spectatorID);
        
        state = SpectatorStates.APPRAISING_THE_HORSES;
        padSpectator.goCheckHorses(spectatorID);
        //escolher um cavalo
        Random r = new Random();
        bestHorse = 1 + r.nextInt(gr.getnHorses()); 
        
        state = SpectatorStates.PLACING_A_BET;
        if(money <1)
            bet = 0;
        else
            bet = (int) ((money - 1) * Math.random()) + 1 ; //+1 para ser no minimo 1€.
        
        bcSpectator.placeABet(spectatorID, bet, bestHorse);
        money = money - bet; 
        
        state = SpectatorStates.WATCHING_A_RACE;
        ccSpectator.goWatchTheRace(spectatorID);
        nRaces--;
        
        if (ccSpectator.haveIWon(spectatorID)) {
             state = SpectatorStates.COLLECTING_THE_GAINS;
        /*double totalApostadoPerdido = bcSpectator.goCollectTheGains(spectatorID);
        int totalApostasVencedor = bcSpectator.getTotalBetValue();
        int ganho = (apos / totalApostasVencedor) * totalApostadoPerdido;
        money = money + bet + ganho;*/
        //System.err.println("\nApostador " + spectatorID + " ganhou : " + (int) ganho + " fica com: " + (int) money);

        } else {

            System.err.println("\nApostador " + spectatorID + " nao apostou no cavalo ganhador. Perdeu: " + bet + " fica com: " + money);
        }
        
        if (nRaces != 0) {
            ccSpectator.waitForTheNextRace(spectatorID);
        } 
        else {
            state = SpectatorStates.CELEBRATING;
            //relaxABit();
        }
        
        
        
       
        
    }  
}
