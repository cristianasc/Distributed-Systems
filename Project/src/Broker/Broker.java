/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broker;

import Stable.*;
import RacingTrack.*;
import ControlCentre.*;
import BettingCentre.*;
import GeneralRepository.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author cristianacarvalho
 */
public class Broker extends Thread{
    
    private BrokerStates state;
    private IStable_Broker stBroker;
    private IStable_Horses stHorses;
    private IRacingTrack_Broker rtBroker;
    private IControlCentre_Broker ccBroker;
    private IBettingCentre_Broker bcBroker;
    private GeneralRepository gr;
    private int nRaces;
    private Bet bet;
    private ArrayList<Bet> bets;
    private HashMap<Integer, ArrayList<Bet>> betsByHorses;
    
    
    public Broker(IBettingCentre_Broker bcBroker, IStable_Broker stBroker, IStable_Horses stHorses, IRacingTrack_Broker rtBroker, IControlCentre_Broker ccBroker, GeneralRepository gr){
        this.stBroker = stBroker;
        this.rtBroker = rtBroker;
        this.ccBroker = ccBroker;
        this.bcBroker = bcBroker;
        this.gr = gr;
        bet = new Bet();
        nRaces = gr.getnRaces();
        bets = new ArrayList<>();
        betsByHorses = new HashMap<>();
    }
    
    @Override
    public void run() {
        state = BrokerStates.OPENING_THE_EVENT;
        
        System.out.print("\nBroker iniciado.");
        summonHorsesToPaddock();
    }

    public void summonHorsesToPaddock(){
        System.out.print("\nBroker está no Control Centre.");
        state = BrokerStates.ANNOUNCING_NEXT_RACE;
        
        //chamar os cavalos para o paddock
        stBroker.summonHorsesToPaddock();
        //chamar os espectadores para o paddock
        ccBroker.summonHorsesToPaddock();
        
        acceptTheBets();
    }
    
    public void acceptTheBets(){
        state = BrokerStates.WAITING_FOR_BETS;
        
        bets = new ArrayList<>();
        betsByHorses = new HashMap<>();
        
        int i = 0;
        do {
            i++;
            bet = bcBroker.acceptTheBets();
            bets = betsByHorses.get(bet.getHorseID());
            if (bets != null) {
                bets.add(bet);
            } else {
                bets = new ArrayList<>();
                bets.add(bet);
            }
            betsByHorses.put(bet.getHorseID(), bets);
        } while (i != gr.getnSpectator());
        
        startTheRace();
    }
    
    public void startTheRace(){
        state = BrokerStates.SUPERVISING_THE_RACE;
        
        //rtBroker.startTheRace();
        //EM FALTA cc.startTheRace();
        
        //a corrida acabou, por isso diminuimos o numero de corridas
        //nRaces--;
        //gr.setnRaces(nRaces);
        
        //reportar cavalo vencedor
        //reportResults();
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
