/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broker;

import Stable.IStable_Broker;
import Stable.IStable_Horses;
import RacingTrack.IRacingTrack_Broker;
import GeneralRepository.GeneralRepository;

/**
 *
 * @author cristianacarvalho
 */
public class Broker extends Thread{
    
    private BrokerStates state;
    private IStable_Broker stBroker;
    private IStable_Horses stHorses;
    private IRacingTrack_Broker rtBroker;
    private GeneralRepository gr;
    private int nRaces;
    
    
    public Broker(IStable_Broker stBroker, IStable_Horses stHorses, IRacingTrack_Broker rtBroker, GeneralRepository gr){
        this.stBroker = stBroker;
        this.rtBroker = rtBroker;
        this.gr = gr;
        nRaces = gr.getnRaces();
    }
    
    @Override
    public void run() {
        state = BrokerStates.OPENING_THE_EVENT;
        
        System.out.print("Broker iniciado.");
        summonHorsesToPaddock();
    }

    public void summonHorsesToPaddock(){
        System.out.print("\nBroker está no Control Centre.");
        state = BrokerStates.ANNOUNCING_NEXT_RACE;
        
        //chamar os cavalos para o paddock
        stBroker.summonHorsesToPaddock();
        //chamar os espectadores para o paddock
        //EM FALTA cc.summonHorsesToPaddock();
        
        acceptTheBets();
    }
    
    public void acceptTheBets(){
        state = BrokerStates.WAITING_FOR_BETS;
        
        //EM FALTA bc.acceptTheBets
        
        startTheRace();
    }
    
    public void startTheRace(){
        state = BrokerStates.SUPERVISING_THE_RACE;
        
        rtBroker.startTheRace();
        //EM FALTA cc.startTheRace();
        
        //a corrida acabou, por isso diminuimos o numero de corridas
        nRaces--;
        gr.setnRaces(nRaces);
        
        //reportar cavalo vencedor
        reportResults();
    }
    
    public void reportResults(){
        //EM FALTA cc.reportResults()
    }
    
    public void honourTheBets(){
        state = BrokerStates.SETTLING_ACCOUNTS;
        //EM FALTA bc.honourTheBets();
    }
    
    public void entertainTheGuests(){
        state = BrokerStates.PLAYING_HOST_AT_THE_BAR;
        //EM FALTA bc.entertainTheGuests();
    }
    
}
