/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broker;

/**
 *
 * @author cristianacarvalho
 */
public enum BrokerStates {
    OPENING_THE_EVENT("OTE"), ANNOUNCING_NEXT_RACE("ANR"), WAITING_FOR_BETS("WFB"), 
    SUPERVISING_THE_RACE("STR"), SETTLING_ACCOUNTS("SAC"), PLAYING_HOST_AT_THE_BAR("PHB");
    
    private String state;
    
    private BrokerStates(String state){
        this.state = state;
    }
    
    @Override
    public String toString(){
        return state;
    }
}
