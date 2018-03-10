/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stable;

import Stable.IStable_Horses;
import GeneralRepository.GeneralRepository;

/**
 *
 * @author cristianacarvalho
 */
public class Stable implements IStable_Horses, IStable_Broker{
    
    private int horseId;
    private boolean callHorses;
    private GeneralRepository gn;
    
    public Stable(){
        horseId = 0;
        callHorses = false;
    }

    /**
     * Método é bloqueado enquanto o broker não chamar os cavalos, ou seja os 
     * cavalos ficam no Stable até o broker os chamar
     *
     */
    @Override
    public synchronized void proceedToStable() {
        notifyAll();
        while (!callHorses) {
            try {
                System.err.println("\nOs cavalos estão no estábulo.");
                wait();
            } catch (InterruptedException ex) {
            }
        }
        //Cavalos em espera foram acordados
        horseId++;
        notifyAll();
    }
    
    @Override
    public void summonHorsesToPaddock() {
        System.err.println("\nBroker chama os Cavalos para paddock.");
        callHorses = true;
        
        // todos os cavalos em espera (no estábulo) serão acordados
        notifyAll();
        
        while (horseId != gn.getnHorses()) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        System.err.println("\nOs cavalos foram chamados pelo Broker");
        
        //horseId e callHorses voltam aos valores inicias
        horseId = 0;
        callHorses = false;
    }
    
}
