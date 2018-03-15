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
    public synchronized void proceedToStable() {
        // notifyAll();
        
        while (!callHorses) {
            try {
                System.out.print("\nOs cavalos estão no estábulo.");
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
        System.out.print("\nBroker chama os Cavalos para paddock.");
        callHorses = true;
        
        // todos os cavalos em espera (no estábulo) serão acordados
        notifyAll();
        
        while (horseId != gr.getnHorses()) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        System.out.print("\nOs cavalos foram chamados pelo Broker.");
        
        //horseId e callHorses voltam aos valores inicias
        horseId = 0;
        callHorses = false;
    }
    
}
