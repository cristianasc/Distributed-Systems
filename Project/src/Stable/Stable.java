/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stable;

import Stable.*;
import GeneralRepository.*;

/**
 *
 * @author cristianacarvalho
 */
public class Stable implements IStable_Horses, IStable_Broker{
    
    private int horseId;
    private boolean callHorses;
    private GeneralRepository gr;
    
    public Stable(GeneralRepository gr){
        horseId = 0;
        callHorses = false;
        this.gr = gr;
    }

    /**
     * Método é bloqueado enquanto o broker não chamar os cavalos, ou seja os 
     * cavalos ficam no Stable até o broker os chamar
     *
     */
    @Override
    public synchronized void proceedToStable(int horseID) {
        //notifyAll();
        
        System.out.print("\nO cavalo "+ horseID + " está no estábulo.");
        while (!callHorses) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        //Cavalos em espera foram acordados
        horseId++;
        notifyAll();
    }
    
    /**
     *
     * 
     */
    @Override
    public synchronized void summonHorsesToPaddock() {
        System.out.print("\nBroker vai chamar os Cavalos para paddock.");
        callHorses = true;
        
        // todos os cavalos em espera (no estábulo) serão acordados
        notifyAll();
        
        while (horseId != gr.getnHorses()) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
              
        //horseId e callHorses voltam aos valores inicias
        horseId = 0;
        callHorses = false;
    }
    
}
