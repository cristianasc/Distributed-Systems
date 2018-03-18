/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RacingTrack;

import GeneralRepository.GeneralRepository;
import java.util.HashMap;

/**
 *
 * @author cristianacarvalho
 */
public class RacingTrack implements IRacingTrack_Horses, IRacingTrack_Broker{
    
    private boolean makeAMove, reportResults, lastHorse;
    private GeneralRepository gn;
    private HashMap<Integer, Integer> positions;
    private int next, horseNum;
    
    public RacingTrack(){
        next = 0;
        makeAMove = false;
        reportResults = false;
        positions = new HashMap<>();
    }
    
    @Override
    public synchronized void proceedToStartLine() {
        
        while (!makeAMove) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
         
    }

    @Override
    public synchronized void makeAMove(){
        
        
    }

    @Override
    public synchronized boolean hasFinishLineBeenCrossed(int horseID) {
        if(positions.get(horseID) >= gn.getDistance())
            return true;
        else
            return false;
    }

    

    @Override
    public synchronized void startTheRace() {
        System.out.print("----------COMEÇA A CORRIDA----------");
        
        makeAMove = true;
        next = 0;
        
        //posições iniciais dos cavalos
        for (int i = 0; i < gn.getnHorses(); i++) {
            positions.put(i, 0);         
        }
       
        //Acordar os cavalos
        notifyAll();
        for (int i = 0; i < gn.getnHorses(); i++)
            System.out.print("Cavalo" + i + "na posição" + positions.get(i));
             
    }

    
    
    
}
