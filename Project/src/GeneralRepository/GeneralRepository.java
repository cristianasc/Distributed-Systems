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
    private int nHorses, nSpectators, nRaces, nWinners, distance;
    private HashMap<Integer, Integer> horsePositions;
    private HashMap<Integer, Integer> horseSkills;
    private HashMap<Integer, Bet> betsPerSpectator;
    private int nWinners;
    private int currentRace;
    
    /**
     * Construtor da classe
     *
     * @param nHorses - Numero de cavalos
     * @param nRaces - Numero de corridas
     * @param distance - Distância da pista
     * @param betsPerSpectator - HashMap, com Key= ID do apostador e Value= valor
     * obtido no total de corridas
     */
    
    public GeneralRepository(int nHorses, int nSpectators, int nRaces, int distance, int nWinners){
        this.nHorses = nHorses;
        this.nSpectators = nSpectators;
        this.nRaces = nRaces;
        this.distance = distance;
        horsePositions = new HashMap<>();
        horseSkills = new HashMap<>();
        betsPerSpectator = new HashMap<>();
        this.nWinners = nWinners;
    }
    
    public int getDistance(){
        return distance;
    }
    
    public void setDistance(){
        this.distance = distance;
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
    
    public int getHorseSkills(int id) {
        return horseSkills.get(id);
    }
    
    public void setHorseSkills(int id, int skiil) {
        horseSkills.put(id, skiil);
    }
    
    public void gethorsePositions(int id) {
        horsePositions.get(id);
    }
    
    public void sethorsePositions(int id, int position) {
        horsePositions.put(id, position);
    }
    
    public void setBetsPerSpectator(int ID, Bet bet) {
        betsPerSpectator.put(ID, bet);
    }
    
    public int getnWinners() {
        return nWinners;
    }

    public void setnWinners(int size) {
        this.nWinners = size;
    }
    
}
