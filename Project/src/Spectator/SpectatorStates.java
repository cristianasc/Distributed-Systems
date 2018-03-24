/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spectator;

/**
 *
 * @author cristianacarvalho
 */
public enum SpectatorStates {
    WAITING_FOR_A_RACE_TO_START("WFRS"), APPRAISING_THE_HORSES("ATH"), PLACING_A_BET("P_B"),
    WATCHING_A_RACE("WAR"), COLLECTING_THE_GAINS("CTG"), CELEBRATING("CEL");
    
    private String state;
    
    private SpectatorStates(String state){
        this.state = state;
    }
    
    @Override
    public String toString(){
        return state;
    }
}
