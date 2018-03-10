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
    OPENING_THE_EVENT, ANNOUNCING_NEXT_RACE, WAITING_FOR_BETS, 
    SUPERVISING_THE_RACE, SETTLING_ACCOUNTS, PLAYING_HOST_AT_THE_BAR;
}
