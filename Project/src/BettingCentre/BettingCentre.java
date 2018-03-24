/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BettingCentre;

import GeneralRepository.Bet;
import GeneralRepository.GeneralRepository;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public class BettingCentre implements IBettingCentre_Broker, IBettingCentre_Spectator {
    
    
    private final GeneralRepository gr;
    private int spectatorCount, nCollects;
    private boolean brokerAcceptingBets, finalBet, newBet, hounourTheBets, finalCollect;
    private Bet bet;
    private double totalValue;
    
    /**
     * Construtor da classe
     * @param gr: General Repository
     */
    public BettingCentre(GeneralRepository gr) {
        this.gr = gr;
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
     * Método que aceita as apostas. 
     * O Broker acorda os espectadores para apostarem. 
     * Enquanto não forem feita uma nova aposta, ou não for feita
     * a última aposta do ultimo espectador/apostador.
     *
     * @return bet - valor total das apostas feitas por um espectador
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
        totalValue = 0;
        return bet;
    }

    /**
     * Método acorda os espectadores para serem pagos pelas suas apostas.
     * O Broker fica bloqueado enquanto não todos os vencedores não forem pagos.
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
    
    /**
     * Método boolean que indica se existem vencedores.
     * @param winners: lista de vencedores
     * @return True se a lista de vencedores não for vazia, ou False se for.
     */
    @Override
    public synchronized boolean areThereAnyWinners(ArrayList<Bet> winners) {
        return winners != null;
    }

    /**
     * Método que indica apenas o que está a acontecer no último estado do ciclo
     * de vida do Broker.
     */
    @Override
    public synchronized void entertainTheGuests() {
        System.out.println("Os convidados estão-se a divertir! ");
    }

    @Override
    public synchronized void relaxABit(int spectatorID, int money) {
        System.out.println("\nApostador " + spectatorID + " vai relaxar..Acabou com " + money + "€.");    }
    }
