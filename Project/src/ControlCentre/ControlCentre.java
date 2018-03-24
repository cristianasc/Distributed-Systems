/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlCentre;

import GeneralRepository.*;
import java.util.ArrayList;
import GeneralRepository.Bet;

/**
 *
 * @author cristianacarvalho
 */
public class ControlCentre implements IControlCentre_Horses, IControlCentre_Broker,
        IControlCentre_Spectator{
    
    private final GeneralRepository gr;
    private boolean lastHorseToPaddock, lastSpectator, reportResults, allReportResults;
    private int nSpectators, nHorses, spec;
    private ArrayList<Bet> winners;
    
    /**
     * Construtor da classe
     * @param gr: General Repository
     */
    public ControlCentre(GeneralRepository gr){
        this.gr = gr;
        lastHorseToPaddock = false;
        lastSpectator = false;
        reportResults = false;
        this.nSpectators = 0;
        this.spec = 0;
        winners = new ArrayList<>();
        this.nHorses = 0;
    }

    @Override
    public synchronized void proceedToPaddock(int horseID) {
        System.out.print("\nO cavalo "+ horseID +" vai para o paddock.");
        nHorses++;
        System.out.print(nHorses);
        if(nHorses==gr.getnHorses()){
            lastHorseToPaddock = true;
            System.out.print("\nTodos os cavalos estão no Paddock.");
            notifyAll();
        }
    }

    /**
     * Método que acorda os espectadores que estão no Watching Stand.
     * O Broker é bloqueado enquanto não forem reportados todos os resultados.
     * 
     * @param betlist: ArrayList do tipo Bet com todos os apostadores que ganham.
     */
    @Override
    public synchronized void reportResults(ArrayList<Bet> betlist) {
        
        System.out.print("\nA reportar resultados.");
        winners = betlist;
        reportResults = true;
        notifyAll();
        
        while (!allReportResults) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        reportResults = false;
        allReportResults = false;
        spec = 0;
        nHorses = 0; 

    }

    /**
     * Método que bloqueia o broker até que o último espectador chegue 
     * ao paddock.
     */
    @Override
    public synchronized void summonHorsesToPaddock() {
        while (!lastSpectator) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        lastHorseToPaddock = false;
        lastSpectator = false;
        nSpectators = 0;
    }

    @Override
    public synchronized void goWatchTheRace(int spectatorID) {
         System.out.print("\nApostador " + spectatorID + " vai para a Watching Stand.");
         while (!reportResults) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
         
        spec++;
        if (spec == gr.getnSpectator()) {
            allReportResults = true;
        }
        
        notifyAll();
    }

    @Override
    public synchronized void waitForTheNextRace(int spectatorID) {
        
        while (!lastHorseToPaddock) {
            try {
                wait();
            } catch (InterruptedException ex) {
                
            }
        }
        
        nSpectators++; 
        System.out.print("\nO espectador "+ spectatorID +" vai para o Paddock.");
        if(nSpectators == gr.getnSpectator()){
            lastSpectator = true;
            notifyAll();
        }
    }

    @Override
    public synchronized boolean haveIWon(int spectator) {
        if (winners == null){
            return false;
        }
        for (int i=0; i<winners.size(); i++){
            if (spectator == winners.get(i).getSpectatorID()){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ArrayList<Bet> getWinners(){
        return winners;
    } 
    
    
}
