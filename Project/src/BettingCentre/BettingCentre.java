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
    
    /**
     * Método que aceita apostas de um dado espectador, sobre um certo cavalo num dado valor.
     * A thread spectator fica em wait enquanto não estiverem feitas todas
     * as apostas, e acorda os espetadores após estarem concluidas todas as
     * apostas.
     *
     * @param punterID - ID do apostador
     * @param value - Valor da aposta
     * @param horseID - ID do cavalo a apostar
     */
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
        gr.getBetsPerSpectator(spectatorID);
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
    
   /**
     * Método para recolher as apostas. A thread acorda os espectadores para
     * apostarem e entra em wait enquanto não forem feitas apostas, ou não for feita
     * a ultima aposta do ultimo apostador.
     *
     * @return bet - valor total das apostas
     */
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

    /**
     * Método responsável por chamar os espectadores e pagar os respetivos
     * lucros das apostas conforme o resultado da corrida. O método  acorda os espetadores para irem receber e
     * a thread entra em wait enquanto não forem entregues os dividendos a todos
     * os apostadores vencedores.
     *
     * @param betList - lista de apostadores vencedores
     */
    @Override
    public synchronized void honourTheBets() {
        System.out.print("\nBroker paga.");
        
        while (finalCollect == false) {
            hounourTheBets = true;
            notifyAll();
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        
        hounourTheBets = false;
        finalCollect = false;
        nCollects = 0;
    }
    
    /**
     * Método que acorda um dado espectador (spectatorID) para que este possa levantar o seu lucro da aposta caso tenha ganho.
     * A thread entra em wait enquanto não for pago o devido montante aos
     * apostadores vencedores. O espectador é acordado pelo broker assim que a transação seja completada.
     *
     * @param spectatorID - ID do espectador
     */
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
        
        System.out.print("\n ->"+gr.getnWinners() + ", " + nCollects);
        
        if (nCollects == gr.getnWinners()) {
            finalCollect = true;
            notifyAll();
        }
    }
    
    @Override
    public synchronized boolean areThereAnyWinners(ArrayList<Bet> winners) {
        if (winners == null) {
            return false;
        }
        return true;
    }

    @Override
    public synchronized void entertainTheGuests() {
        System.out.println("Os convidados estão-se a divertir! ");
    }

    @Override
    public synchronized void relaxABit(int spectatorID, int money) {
        System.out.println("\nApostador " + spectatorID + " vai relaxar..Acabou com " + money + "€.");    }
    }
