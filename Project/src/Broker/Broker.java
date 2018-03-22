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
    private int nRaces, winnerHorse, moneyBet;
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
        for (int k = 0; k < gr.getnRaces(); k++){
            state = BrokerStates.OPENING_THE_EVENT;
        
            System.out.print("\nBroker iniciado.");
            System.out.print("\nBroker está no Control Centre.");
            state = BrokerStates.ANNOUNCING_NEXT_RACE;

            //chamar os cavalos para o paddock
            stBroker.summonHorsesToPaddock();
            //chamar os espectadores para o paddock
            ccBroker.summonHorsesToPaddock();

            state = BrokerStates.WAITING_FOR_BETS;

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

            state = BrokerStates.SUPERVISING_THE_RACE;
            rtBroker.startTheRace();

            //a corrida acabou, por isso diminuimos o numero de corridas
            nRaces--;
            gr.setnRaces(nRaces);
            System.out.print("\nCavalo vencedor: Cavalo " + gr.getHorseWinner() + ".");

            //reportar cavalo vencedor
            if (betsByHorses.get(gr.getHorseWinner()) == null){
                bets = null;
                ccBroker.reportResults(bets);
                 
            }
            else{
                bets = betsByHorses.get(gr.getHorseWinner());
                //calcular valor das bets
                for (int j = 0; j < bets.size(); j++) {
                    moneyBet = (int) (moneyBet + bets.get(j).Betvalue);
                }
                ccBroker.reportResults(bets);
               
            }
            
            if (ccBroker.areThereAnyWinners()){
                System.err.println("\n print aquiiiiiiiiiiiiiii");
                state = BrokerStates.SETTLING_ACCOUNTS;        
                bcBroker.honourTheBets();
            }
        }
        
        // ENTERTAIN THE GUESTS (FORA DO FOR)
        state = BrokerStates.PLAYING_HOST_AT_THE_BAR;
        bcBroker.entertainTheGuests();
    }
}
