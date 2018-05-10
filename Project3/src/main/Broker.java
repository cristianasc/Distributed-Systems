/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Stable.*;
import RacingTrack.*;
import ControlCentre.*;
import BettingCentre.*;
import Broker.BrokerStates;
import GeneralRepository.*;
import interfaces.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author cristianacarvalho
 */
public class Broker extends Thread{
    
    private BrokerStates state;
    private final IStable st;
    private final IRacingTrack rt;
    private final IControlCentre cc;
    private final IBettingCentre bc;
    private final IGeneralRepository gr;
    private int nRaces, moneyBet;
    private Bet bet;
    private ArrayList<Bet> bets;
    private HashMap<Integer, ArrayList<Bet>> betsByHorses;
    
    public Broker(IBettingCentre bc, IStable st, IRacingTrack rt, IControlCentre cc, IGeneralRepository gr){
        this.st = st;
        this.rt = rt;
        this.cc = cc;
        this.bc = bc;
        this.gr = gr;
        bet = new Bet();
        nRaces = gr.getnRaces();
        bets = new ArrayList<>();
        betsByHorses = new HashMap<>();
    }
    
    @Override
    public void run() {
        for (int k = 1; k <= gr.getnRaces(); k++){
            state = BrokerStates.OPENING_THE_EVENT;
            gr.setBrokerState(state);
        
            System.out.print("\nBroker iniciado.");
            System.out.print("\nBroker está no Control Centre.");
            state = BrokerStates.ANNOUNCING_NEXT_RACE;
            gr.setBrokerState(state);

            //chamar os cavalos para o paddock
            st.summonHorsesToPaddock();
            //chamar os espectadores para o paddock
            cc.summonHorsesToPaddock();

            state = BrokerStates.WAITING_FOR_BETS;
            gr.setBrokerState(state);
            
            bets = new ArrayList<>();
            betsByHorses = new HashMap<>();
            int i = 0;
            
            //adicionar as bets de dos espetadores
            do {
                i++;
                bet = bc.acceptTheBets();
                bets = betsByHorses.get(bet.getHorseID());
                if (bets != null) {
                    bets.add(bet);
                }
                else if (bets == null) {
                    bets = new ArrayList<>();
                    bets.add(bet);
                }
                betsByHorses.put(bet.getHorseID(), bets);
            } while (i != gr.getnSpectator());

            state = BrokerStates.SUPERVISING_THE_RACE;
            gr.setBrokerState(state);
            rt.startTheRace();
            
            System.out.print("\nCavalo vencedor: Cavalo " + gr.getHorseWinnerID() + ".");
            
            //reportar cavalo vencedor 
            if (betsByHorses.get(gr.getHorseWinnerID()) == null){
                bets = null;
                cc.reportResults(bets);
            }
            else{
                bets = betsByHorses.get(gr.getHorseWinnerID());
                gr.setnWinners(bets.size());
                //calcular valor das bets
                for (int j = 0; j < bets.size(); j++) {
                    moneyBet = (int) (moneyBet + bets.get(j).getBetvalue());
                }
                cc.reportResults(bets);
            }
            
            
            if (bc.areThereAnyWinners(betsByHorses.get(gr.getHorseWinnerID()))){
                state = BrokerStates.SETTLING_ACCOUNTS; 
                gr.setBrokerState(state);
                bc.honourTheBets();
            }
        }
        
        state = BrokerStates.PLAYING_HOST_AT_THE_BAR;
        gr.setBrokerState(state);
        bc.entertainTheGuests();
    }
}
