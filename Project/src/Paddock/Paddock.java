/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paddock;

import GeneralRepository.GeneralRepository;

/**
 *
 * @author cristianacarvalho
 */
public class Paddock implements IPaddock_Horses, IPaddock_Spectator{
    
    private int horseId, horseNTotal, spectator, spectatorNTotal, spectatorToBet;
    private boolean goCheckHorses, goToStartLine, horsesProcessedToStartLine;
    private GeneralRepository gn;
    
    public Paddock(){
        horseNTotal = gn.getnHorses();
        spectatorNTotal = gn.getnSpectator();
        horseId = 0;
        spectator = 0;
        spectatorToBet = 0;
        goCheckHorses = false;
        horsesProcessedToStartLine = false;
    }
    
    @Override
    public synchronized void proceedToPaddock() {
        while (!goCheckHorses) {
            try {
                System.out.println("\nOs espetadores estão no Watching Stand.");
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        System.out.println("\nCavalo" + horseId + "a ir para a StartLine.");
        horseId++;
        
        if(horseId == horseNTotal){
            goToStartLine = true;
            horseId = 0;
            notifyAll();
            goCheckHorses = false;
        }
    }

    @Override
    public synchronized void goCheckHorses(int spectatorID) {
        spectator++;
        if(spectator == spectatorNTotal){
            goCheckHorses = true;
            //acorda todos os cavalos 
            notifyAll();
        }
        
        while (!goToStartLine) {
            try {
                System.out.println("\nOs espetadores estão no paddock a ver os cavalos.");
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        System.out.println("\nO espectador"+ spectatorID + "vai apostar.");
        spectatorToBet++;
        if (spectatorToBet == spectatorNTotal){
            spectatorToBet = 0;
            spectator = 0;
            horsesProcessedToStartLine = true;
        }
    }
}
