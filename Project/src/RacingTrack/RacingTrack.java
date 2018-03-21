/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RacingTrack;

import GeneralRepository.GeneralRepository;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author cristianacarvalho
 */
public class RacingTrack implements IRacingTrack_Horses, IRacingTrack_Broker{
    
    private boolean makeAMove, reportResults, lastHorse;
    private GeneralRepository gn;
    private HashMap<Integer, Integer> positions;
    private int next, nHorses, position, nHorsesInRace;
    
    public RacingTrack(GeneralRepository gn){
        this.gn = gn;
        this.nHorses = gn.getnHorses();
        next = 0;
        makeAMove = false;
        reportResults = false;
        positions = new HashMap<>();
        nHorsesInRace = gn.getnHorses();
    }
    
    @Override
    public synchronized void proceedToStartLine(int horseID) {
        while (!makeAMove) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }         
    }

    @Override
    public synchronized void makeAMove(int horse, int move){
        //cavalo espera até ser a sua vez de se mover
        while (horse != next) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        //calcular movimento
        move = 1 + (int) (Math.random() * ((move + 1)));
        if (positions.containsKey(horse))
            position = positions.get(horse);
        else
            position = 0;
        
        position += move;
        positions.put(horse, position);
       
        gn.sethorsePositions(horse, position);
        System.out.print("\nCavalo " + horse+ " mexeu-se " + move + ", fica na posição " + position);
        
        if (position >= gn.getDistance()){
            System.out.print("\nCavalo " + horse+ " passou a meta.");
            
            
            if(nHorsesInRace == gn.getnHorses())
                gn.setHorseWinner(horse);
            
            if (positions.containsKey(horse))
                positions.remove(horse);
            
            nHorsesInRace--; 
        }
        
        if (nHorsesInRace != 0){
            next = ((next % nHorses) + 1);
            if (!positions.containsKey(next)) {
                do {
                    next = positions.keySet().iterator().next();
                } while (!positions.containsKey(next));
            }
        }
        
        if (nHorsesInRace == 0){
            System.out.print("\nO último cavalo passou a meta.");
            lastHorse = true;
        }
        
        notifyAll();        
    }

    @Override
    public synchronized boolean hasFinishLineBeenCrossed(int horseID) {
        if(!positions.containsKey(horseID))
            return true;
        else
            return false;
    }

    @Override
    public synchronized void startTheRace() {
        System.out.print("\nCOMEÇA A CORRIDA");
        
        next = 1;
        makeAMove = true;
        
        //posições iniciais dos cavalos
        for (int i = 0; i < gn.getnHorses(); i++) {
            positions.put((i + 1), 0);          // repoe posições dos cavalos a zero
        }
        
        //Acordar os cavalos
        notifyAll();
        for (int i = 0; i < gn.getnHorses(); i++){
            System.out.print("\nCavalo " + (i+1) + " na posição " + positions.get(i+1));
        }
        
         while (!lastHorse) {
            try {
                wait();
            } catch (InterruptedException ex) {
                
            }
        }
        lastHorse = false;
        makeAMove = false;
        System.out.print("\nFIM DA CORRIDA.");
    }    
}
