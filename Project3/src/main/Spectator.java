/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import ControlCentre.*;
import GeneralRepository.*;
import Paddock.*;
import BettingCentre.*;
import Spectator.SpectatorStates;
import interfaces.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cristianacarvalho
 */
public class Spectator extends Thread{
    
    private SpectatorStates state;
    private final IControlCentre cc;
    private final IPaddock pad;
    private final IBettingCentre bc;
    private final IGeneralRepository gr;
    private int spectatorID, bestHorse, money, total, bet,nRaces;
    private Bet aposta;
    private ArrayList<Bet> bets;
    
    public Spectator(IBettingCentre bc, IControlCentre cc, IPaddock pad, int spectatorID, IGeneralRepository gr){
        this.cc = cc;
        this.pad = pad;
        this.bc = bc;
        this.gr = gr;
        this.spectatorID = spectatorID;
        this.money = 5;
        this.bet = 0;
        this.nRaces = gr.getnRaces();
    }
    
    @Override
    public void run() {
        state = SpectatorStates.WAITING_FOR_A_RACE_TO_START;
        gr.setSpectatorState(spectatorID,state);
        gr.setSpectatorMoney(spectatorID,money);
        gr.setSpectatorBet(spectatorID,bet);
        waitForTheNextRace();   
    }
    
    public void waitForTheNextRace(){
        System.out.print("\nO espectador " + spectatorID + " está à espera da próxima corrida.");
        cc.waitForTheNextRace(spectatorID);
        goCheckHorses();
    }
    
    public void goCheckHorses(){
        state = SpectatorStates.APPRAISING_THE_HORSES;
        pad.goCheckHorses(spectatorID);
        gr.setSpectatorState(spectatorID,state);
        
        //escolher um cavalo
        Random r = new Random();
        bestHorse = 1 + r.nextInt(gr.getnHorses()); 
        placeABet();
    }
    
    public void placeABet(){
        state = SpectatorStates.PLACING_A_BET;
        if(money < 1){
            bet = 0;
        }            
        else{
            bet = (int) ((money - 1) * Math.random()) + 1 ; //+1 para ser no minimo 1€.
        }

        bc.placeABet(spectatorID, bet, bestHorse);
        money = money - bet;
        gr.setSpectatorState(spectatorID,state);
        gr.setSpectatorMoney(spectatorID,money);
        gr.setSpectatorBet(spectatorID,bet);
        goWatchTheRace();
    }
    
    public void goWatchTheRace(){
        state = SpectatorStates.WATCHING_A_RACE;
        cc.goWatchTheRace(spectatorID);
        gr.setSpectatorState(spectatorID,state);
        nRaces--;
        gr.setActualRace(nRaces);
        
        if (cc.haveIWon(spectatorID)) {
            state = SpectatorStates.COLLECTING_THE_GAINS;
            bc.goCollectTheGains(spectatorID);
            gr.setSpectatorState(spectatorID,state);
            
        } else {
            System.out.print("\nApostador " + spectatorID + " perdeu.");
        }
        
        if (nRaces != 0) {
            state = SpectatorStates.WAITING_FOR_A_RACE_TO_START;
            gr.setSpectatorState(spectatorID,state);
            waitForTheNextRace();
        }
        else {
            //RELAX A BIT
            state = SpectatorStates.CELEBRATING;
            bc.relaxABit(spectatorID);
            gr.setSpectatorState(spectatorID,state);
        }
    }
}
