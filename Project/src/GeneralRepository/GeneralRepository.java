/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralRepository;

import java.util.HashMap;

/**
 *
 * @author cristianacarvalho
 */
public class GeneralRepository {
    private int nHorses, nSpectators, nRaces, nWinners;
    private HashMap<Integer, Integer> horsePositions;
    private HashMap<Integer, Integer> horseSkiils;
    
    /**
     * Construtor da classe
     *
     * @param nSpect - Numero de espetadores
     * @param nHorses - Numero de cavalos
     * @param nRaces - Numero de corridas
     */
    
    public GeneralRepository(int nHorses, int nSpectators, int nRaces){
        this.nHorses = nHorses;
        this.nSpectators = nSpectators;
        this.nRaces = nRaces;
        horsePositions = new HashMap<>();
        horseSkiils = new HashMap<>();
    }
    
    public int getnHorses(){
        return nHorses;
    }
    
    public void setnHorses(int nHorses) {
        this.nHorses = nHorses;
    }
    
    public int getnSpectator(){
        return nSpectators;
    }
    
    public void setnSpectator(int nSpectators) {
        this.nSpectators = nSpectators;
    }
    
    public int getnRaces(){
        return nRaces;
    }
    
    public void setnRaces(int nRaces) {
        this.nRaces = nRaces;
    }
    
    public int getHorseSkiils(int id) {
        return horseSkiils.get(id);
    }
    
    public void setHorseSkiils(int id, int skiil) {
        horseSkiils.put(id, skiil);
    }
    
    public void gethorsePositions(int id) {
        horsePositions.get(id);
    }
    
    public void sethorsePositions(int id, int position) {
        horsePositions.put(id, position);
    }
    
}
