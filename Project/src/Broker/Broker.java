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
        for (int k = 1; k <= gr.getnRaces(); k++){
            state = BrokerStates.OPENING_THE_EVENT;
            gr.setBrokerState(state);
        
            System.out.print("\nBroker iniciado.");
            System.out.print("\nBroker está no Control Centre.");
            state = BrokerStates.ANNOUNCING_NEXT_RACE;
            gr.setBrokerState(state);

            //chamar os cavalos para o paddock
            stBroker.summonHorsesToPaddock();
            //chamar os espectadores para o paddock
            ccBroker.summonHorsesToPaddock();

            state = BrokerStates.WAITING_FOR_BETS;
            gr.setBrokerState(state);
            bets = new ArrayList<>();
            betsByHorses = new HashMap<>();
            int i = 0;
            do {
                i++;
                bet = bcBroker.acceptTheBets();
                bets = betsByHorses.get(bet.getHorseID());
                if (bets != null) {
                    System.out.println(bets);
                    bets.add(bet);
                }
                else if (bets == null) {
                    //System.err.println("Apostassssssssa aqqudfsd");
                    bets = new ArrayList<>();
                    bets.add(bet);
                }
                betsByHorses.put(bet.getHorseID(), bets);
            } while (i != gr.getnSpectator());

            state = BrokerStates.SUPERVISING_THE_RACE;
            gr.setBrokerState(state);
            rtBroker.startTheRace();

            /*nRaces--;
            gr.setnRaces(nRaces);
            */
            System.out.print("\nCavalo vencedor: Cavalo " + gr.getHorseWinner() + ".");
            for (int l = 1; l < betsByHorses.size(); l++) {
                    System.out.print("\nVENCEDORES:" + betsByHorses.get(i));
                }
            
            //reportar cavalo vencedor
            if (betsByHorses.get(gr.getHorseWinner()) == null){
                bets = null;
                ccBroker.reportResults(bets);
            }
            else{
                bets = betsByHorses.get(gr.getHorseWinner());
                gr.setnWinners(bets.size());
                //calcular valor das bets
                for (int j = 0; j < bets.size(); j++) {
                    moneyBet = (int) (moneyBet + bets.get(j).getBetvalue());
                }
                ccBroker.reportResults(bets);
            }
            if (bcBroker.areThereAnyWinners(betsByHorses.get(gr.getHorseWinner()))){
                state = BrokerStates.SETTLING_ACCOUNTS; 
                gr.setBrokerState(state);
                bcBroker.honourTheBets();
            }
            System.out.print("FIM BROKER!");
        }
        
        // ENTERTAIN THE GUESTS (FORA DO FOR)
        state = BrokerStates.PLAYING_HOST_AT_THE_BAR;
        gr.setBrokerState(state);
        bcBroker.entertainTheGuests();
    }
}
