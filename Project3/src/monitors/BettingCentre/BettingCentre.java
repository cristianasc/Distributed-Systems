package monitors.BettingCentre;

import GeneralRepository.*;
import interfaces.*;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public class BettingCentre implements IBettingCentre{
    
    
    private final IGeneralRepository gr;
    private int spectatorCount, nCollects;
    private boolean brokerAcceptingBets, finalBet, newBet, hounourTheBets, finalCollect;
    private Bet bet;
    private double totalValue;
    
    /**
     * Construtor da classe
     * @param gr: General Repository
     */
    public BettingCentre(IGeneralRepository gr) {
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
     * Método para fazer as apostas de um dado espectador.
     * O espetador fica bloqueado enquanto não tiveram as apostas todas feitas e 
     * acorda-os quando estiverem todas concluídas.
     *
     * @param spectatorID - ID do apostador
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
     * Método que acorda um dado espectador para ir buscar o valor ganho.
     * O espectador fica bloqueanto enquanto não for pago o valor aos vencedores.
     * O espectador é acordado assim que estiver tudo completo.
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
        System.out.print("\nOs convidados estão-se a divertir! ");
    }
    
    /**
     * Método que indica apenas o que está a acontecer no último estado do ciclo
     * de vida do Espectador.
     */
    @Override
    public synchronized void relaxABit(int spectatorID) {
        System.out.print("\nApostador " + spectatorID + " vai relaxar.");    
    }
}
