/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stable;

import GeneralRepository.*;

/**
 *
 * @author cristianacarvalho
 */
public class Stable implements IStable_Horses, IStable_Broker{
    
    private int horseId;
    private boolean callHorses;
    private final GeneralRepository gr;
    
    /**
     * Construtor da classe
     * @param gr: General Repository
     */
    public Stable(GeneralRepository gr){
        horseId = 0;
        callHorses = false;
        this.gr = gr;
    }

    /**
     * Método para chamar os cavalos para o estábulo.
     * Os cavalos são bloqueados enquanto o Broker não tiver chamados todos,
     * após ter chamado todos estes são acordados.
     *
     * @param horseID: ID do cavalo
     */
    @Override
    public synchronized void proceedToStable(int horseID) {
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
     * Método para chamar os cavalos para o paddock. Os cavalos são acordados 
     * pelo broker. Enquanto não chamar os cavalos todos o broker fica 
     * em espera.
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
