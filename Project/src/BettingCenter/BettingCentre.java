package BettingCenter;

import GeneralRepository.Bet;
import GeneralRepository.GeneralRepository;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BettingCentre implements IBettingCentre_Broker, IBettingCentre_Spectator {
    
    public boolean finalBet;
    private GeneralRepository rpt;
    private int punterCount, collectscount;
    private boolean managerAcceptingBets = true;
    private Bet bet;
    private boolean newBetDone;
    private ArrayList<Bet> losers;
    private ArrayList< ArrayList<Bet>> loserList;
    private boolean ManagerHounourTheBets;
    private boolean finalCollect;
    private double totalGet;
    
    public BettingCentre(GeneralRepository rpt) {
        this.rpt = rpt;
        losers = new ArrayList<>();
        loserList = new ArrayList<ArrayList<Bet>>();
        managerAcceptingBets = false;
        newBetDone = false;
        ManagerHounourTheBets = false;
        finalCollect = false;
        punterCount = 0;
        collectscount = 0;
        totalGet = 0;
    }

    // NECESSÁRIO VERIFICAR ESTA FUNÇÃO, Provavelmente está mal !!!
    
    @Override
    public synchronized void acceptTheBets(int spectatorID, double value, int horseID) {
        while (!managerAcceptingBets) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        bet = new Bet();
        bet.setSpectatorID(spectatorID);
        bet.setBetvalue(value);
        bet.setHorseID(horseID);
        rpt.setBetsPerSpectator(spectatorID, bet);

        newBetDone = true;
        notifyAll();

        managerAcceptingBets = false;
        punterCount++;
        if (punterCount == rpt.getnSpectator()) {
            finalBet = true;
            notifyAll();
            punterCount = 0;
        }
        System.err.println("\nApostador " + spectatorID + " apostou " + (int) value + " €" + " no cavalo " + horseID + ".");

    }

    @Override
    public synchronized void honourTheBets() {
        System.err.println("\nManager paga a quem ganhou (Inicio). Tamanho da lista de perdedores: " + loserList.size());
        this.loserList = loserList;
        while (finalCollect == false) {
            ManagerHounourTheBets = true;
            notifyAll();
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ManagerHounourTheBets = false;
        finalCollect = false;
        collectscount = 0;
    }

    /**
     * Método que aceita apostas de um punterID, sobre um horseID no valor de
     * value A thread punter entra em wait enquanto não estiverem feitas todas
     * as apostas, e acorda os espetadores após estarem concluidas todas as
     * apostas.
     *
     * @param punterID - ID do apostador
     * @param value - Valor da aposta
     * @param horseID - ID do cavalo a apostar
     */
    public synchronized void placeABet(int spectatorID, double value, int horseID) {
        while (!managerAcceptingBets) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        bet = new Bet();
        bet.setSpectatorID(spectatorID);
        bet.setBetvalue(value);
        bet.setHorseID(horseID);
        rpt.setBetsPerSpectator(spectatorID, bet);

        newBetDone = true;
        notifyAll();

        managerAcceptingBets = false;
        punterCount++;
        if (punterCount == rpt.getnSpectator()) {
            finalBet = true;
            notifyAll();
            punterCount = 0;
        }
        System.err.println("\nApostador " + spectatorID + " apostou " + (int) value + " €" + " no cavalo " + horseID + ".");

    }

    @Override
    public synchronized double goCollectTheGains(int spectatorID) {
        System.err.println("\nApostador " + spectatorID + " apostou no cavalo ganhador, vai receber prémio!");

        while (!ManagerHounourTheBets) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(BettingCentre.class.getName()).log(Level.SEVERE, null, ex);
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
        if (collectscount == rpt.getnWinners()) {
            finalCollect = true;
            notifyAll();
        }

        return gain;
    }
}
