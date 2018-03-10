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
public class Broker extends Thread{
    
    private BrokerStates state;
    
    public Broker(){
    }
    
    @Override
    public void run() {
        state = BrokerStates.OPENING_THE_EVENT;

        summonHorsesToPaddock();
    }

    public void summonHorsesToPaddock(){
        state = BrokerStates.ANNOUNCING_NEXT_RACE;
    }
    
    public void acceptTheBets(){
        state = BrokerStates.WAITING_FOR_BETS;
    }
    
    public void startTheRace(){
        state = BrokerStates.SUPERVISING_THE_RACE;
    }
    
    public void reportResults(){
        
    }
    
    public void honourTheBets(){
        state = BrokerStates.SETTLING_ACCOUNTS;
    }
    
    public void entertainTheGuests(){
        state = BrokerStates.PLAYING_HOST_AT_THE_BAR;
    }
    
}
