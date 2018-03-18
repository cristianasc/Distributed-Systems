package BettingCentre;

import GeneralRepository.Bet;
import GeneralRepository.GeneralRepository;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BettingCentre implements IBettingCentre_Broker, IBettingCentre_Spectator {
    
    public boolean finalBet;
    private GeneralRepository gr;
    private int spectatorCount, collectscount;
    private boolean brokerAcceptingBets;
    private Bet bet;
    private boolean newBet;
    private ArrayList<Bet> losers;
    private ArrayList< ArrayList<Bet>> loserList;
    private boolean ManagerHounourTheBets;
    private boolean finalCollect;
    private double totalGet;
    
    public BettingCentre(GeneralRepository gr) {
        this.gr = gr;
        losers = new ArrayList<>();
        loserList = new ArrayList<ArrayList<Bet>>();
        brokerAcceptingBets = false;
        newBet = false;
        ManagerHounourTheBets = false;
        finalCollect = false;
        spectatorCount = 0;
        collectscount = 0;
        totalGet = 0;
    }

    @Override
    public synchronized Bet acceptTheBets() {
        brokerAcceptingBets = true;
        notifyAll();
        
        while (!newBet && finalBet != true) {
            try {
                wait();
            } catch (InterruptedException ex) {
                
            }
        }

        newBet = false;
        finalBet = false;
        losers = new ArrayList<>();
        totalGet = 0;
        return bet;
    }

    @Override
    public synchronized void honourTheBets() {
        System.err.print("\nManager paga a quem ganhou (Inicio). Tamanho da lista de perdedores: " + loserList.size());
        this.loserList = loserList;
        while (finalCollect == false) {
            ManagerHounourTheBets = true;
            notifyAll();
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        ManagerHounourTheBets = false;
        finalCollect = false;
        collectscount = 0;
    }

   
    public synchronized void placeABet(int spectatorID, double value, int horseID) {
        while (!brokerAcceptingBets) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }

        bet = new Bet();
        bet.setSpectatorID(spectatorID);
        bet.setBetvalue(value);
        bet.setHorseID(horseID);
        gr.setBetsPerSpectator(spectatorID, bet);

        newBet = true;
        notifyAll();

        brokerAcceptingBets = false;
        spectatorCount++;
        if (spectatorCount == gr.getnSpectator()) {
            finalBet = true;
            notifyAll();
            spectatorCount = 0;
        }
        System.out.print("\nApostador " + spectatorID + " apostou"  + " no cavalo " + horseID +", " + (int) value + " €.");

    }

    @Override
    public synchronized double goCollectTheGains(int spectatorID) {
        System.out.print("\nApostador " + spectatorID + " apostou no cavalo ganhador, vai receber prémio!");

        while (!ManagerHounourTheBets) {
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        notifyAll();
        collectscount++;
        double gain = 0;
        for (int i = 0; i < loserList.size(); i++) {
            losers = loserList.get(i);

            for (int j = 0; j < losers.size(); j++) {
                gain = gain + losers.get(j).Betvalue;
            }
        }
        if (collectscount == gr.getnWinners()) {
            finalCollect = true;
            notifyAll();
        }

        return gain;
    }

    @Override
    public void areThereAnyWinners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void entertainTheGuests() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void relaxABit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
