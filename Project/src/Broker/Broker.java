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
                System.out.print("OLA" + bet.getSpectatorID() +", "+ bet.getHorseID());
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
            rtBroker.startTheRace();

            /*nRaces--;
            gr.setnRaces(nRaces);
            */
            System.out.print("\nCavalo vencedor: Cavalo " + gr.getHorseWinner() + ".");

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
                for (int l = 0; l < bets.size(); l++) {
                    System.out.print("VENCEDORES1:" + bets.get(l).getSpectatorID());
                }
                
                state = BrokerStates.SETTLING_ACCOUNTS;        
                bcBroker.honourTheBets();
            }
            System.out.print("FIM BROKER!");
        }
        
        // ENTERTAIN THE GUESTS (FORA DO FOR)
        state = BrokerStates.PLAYING_HOST_AT_THE_BAR;
        bcBroker.entertainTheGuests();
    }
}
