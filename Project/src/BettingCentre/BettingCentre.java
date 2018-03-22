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
    private double totalValue;
    
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
        System.out.print("\nManager paga a quem ganhou (Inicio). Tamanho da lista de perdedores: " + loserList.size());
        this.loserList = loserList;
        System.err.println("Helloooooo");

        if (finalCollect == false){
            ManagerHounourTheBets = true;
            notifyAll();
        }
        while (finalCollect == false) {            
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        ManagerHounourTheBets = false;
        finalCollect = false;
        collectscount = 0;
    }
    
    @Override
    public synchronized void goCollectTheGains(int spectatorID) {
        System.out.print("\nApostador " + spectatorID + " apostou no cavalo ganhador, vai receber prémio!");

        while (!ManagerHounourTheBets) {
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        notifyAll();
        collectscount++;
        
        if (collectscount == gr.getnWinners()) {
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
