package BettingCentre;

import GeneralRepository.Bet;
import GeneralRepository.GeneralRepository;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BettingCentre implements IBettingCentre_Broker, IBettingCentre_Spectator {
    
    public boolean finalBet;
    private GeneralRepository gr;
    private int spectatorCount, nCollects;
    private boolean brokerAcceptingBets;
    private Bet bet;
    private boolean newBet;
    private ArrayList<Bet> losers;
    private ArrayList< ArrayList<Bet>> loserList;
    private boolean hounourTheBets;
    private boolean finalCollect;
    private double totalValue;
    
    public BettingCentre(GeneralRepository gr) {
        this.gr = gr;
        losers = new ArrayList<>();
        loserList = new ArrayList<ArrayList<Bet>>();
        brokerAcceptingBets = false;
        newBet = false;
        hounourTheBets = false;
        finalCollect = false;
        spectatorCount = 0;
        nCollects = 0;
        totalValue = 0;
    }

    public synchronized void placeABet(int spectatorID, int value, int horseID) {
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
        totalValue = 0;
        return bet;
    }

    @Override
    public synchronized void honourTheBets() {
        System.out.print("\nBroker paga.");
        
        if (finalCollect == false){
            hounourTheBets = true;
            notifyAll();
        }
        while (finalCollect == false) {            
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        hounourTheBets = false;
        finalCollect = false;
        nCollects = 0;
    }
    
    @Override
    public synchronized void goCollectTheGains(int spectatorID) {
        System.out.print("\nApostador " + spectatorID + " vai receber prémio.");

        while (!hounourTheBets) {
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        notifyAll();
        nCollects++;
        
        if (nCollects == gr.getnWinners()) {
            finalCollect = true;
            notifyAll();
        }
    }

    @Override
    public synchronized void entertainTheGuests() {
        System.out.println("Os convidados estão-se a divertir! ");
    }

    @Override
    public synchronized void relaxABit(int spectatorID, int money) {
        System.out.println("\nApostador " + spectatorID + " vai relaxar..Acabou com " + money + "€.");    }
    }
