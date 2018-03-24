/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paddock;

import GeneralRepository.*;

/**
 *
 * @author cristianacarvalho
 */
public class Paddock implements IPaddock_Horses, IPaddock_Spectator{
    
    private int nHorse, horseNTotal, spectator, spectatorNTotal, spectatorToBet;
    private boolean goCheckHorses, goToStartLine;
    private final GeneralRepository gr;
    
    public Paddock(GeneralRepository gr){
        this.gr = gr;
        horseNTotal = gr.getnHorses();
        spectatorNTotal = gr.getnSpectator();
        nHorse = 0;
        spectator = 0;
        spectatorToBet = 0;
        goCheckHorses = false;
    }
    
    /**
     * Método que bloqueia os cavalos até que o último espectador chegue ao 
     * Paddock. Quando isto acontece a flag goCheckHorses fica igual a True e o
     * cavalo deixa de estar bloqueado.
     * 
     * @param horseID: ID do Cavalo
     */
    @Override
    public synchronized void proceedToPaddock(int horseID) {
        while (!goCheckHorses) {
            try {
                wait();      
            } catch (InterruptedException ex) {
                
            }
        }
        
        System.out.print("\nCavalo " + horseID + " a ir para a StartLine.");
        nHorse++;
    }

    @Override
    public synchronized void goCheckHorses(int spectatorID) {
        spectator++;
        if(spectator == spectatorNTotal){
            goCheckHorses = true;
            //acorda todos os cavalos 
            notifyAll();
            System.out.print("\nOs espetadores estão no paddock.");
        }
        
        
        while (!goToStartLine) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        System.out.print("\nO espectador "+ spectatorID + " vai apostar.");
        spectatorToBet++;
        if (spectatorToBet == spectatorNTotal){
            spectatorToBet = 0;
            spectator = 0;
        } 
    }

    /**
     * Método que acorda os espectadores quando o último cavalo sair do Paddock.
     */
    @Override
    public synchronized void proceedToStartLine() {
        if(nHorse == horseNTotal){
            System.out.print("\nOs cavalos sairam todos do paddock.");
            goToStartLine = true;
            nHorse = 0;
            notifyAll();
            goCheckHorses = false;
        }
    }
}
