/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlCentre;

import GeneralRepository.*;
import Paddock.*;


/**
 *
 * @author cristianacarvalho
 */
public class ControlCentre implements IControlCentre_Horses, IControlCentre_Broker,
        IControlCentre_Spectator{
    
    private GeneralRepository gr;
    private Paddock pad;
    private boolean lastHorseToPaddock, lastSpectator, reportResults;
    private int nSpectators, nHorses;
    
    
    public ControlCentre(GeneralRepository gr){
        this.gr = gr;
        lastHorseToPaddock = false;
        lastSpectator = false;
        reportResults = false;
        this.nSpectators = 0;
        this.nHorses = 0;
    }

    @Override
    public synchronized void proceedToPaddock(int horseID) {
        System.out.print("\nO cavalo "+ horseID +" vai para o paddock.");
        nHorses++;
        if(nHorses==gr.getnHorses()){
            lastHorseToPaddock = true;
            System.out.print("\nTodos os cavalos estão no Paddock.");
            notifyAll();
            
        }
    }

    @Override
    public synchronized void makeAMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void reportResults() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
    public synchronized void startTheRace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void haveIWon() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
