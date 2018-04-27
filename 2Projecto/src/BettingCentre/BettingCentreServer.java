/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BettingCentre;

import BettingCentre.*;
import Clients.Msg;
import Clients.MsgType;
import GeneralRepository.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public class BettingCentreServer extends Thread{
    
    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IBettingCentre_Broker bcBroker;
    private IBettingCentre_Spectator bcSpectator;
    

    /**
     * Construtor da classe servidor para o Stable, recebe como parâmetro uma
     * instancia da interface IStable, IStable_Broker e IStable_Horses, e uma porta por onde o servidor vai
     * receber as mensagens
     *
     * @param stable Instancia da interface IStable que toma o valor de StableLocal
     * @param stBroker Instancia da interface IStable_Broker
     * @param stHorses Instancia da interface IStable_Horses
     * @param port Porta onde o servidor fica a "escuta" das mensagens
     */
    public BettingCentreServer(IBettingCentre_Broker bcBroker, IBettingCentre_Spectator bcSpectator, int port) {
        this.bcBroker = bcBroker;
        this.bcSpectator = bcSpectator;
        this.port = port;
        System.out.printf("\nPADDOCK SERVER\n");
    }
    
    
    
    /**
     * Funcão que inicializa a thread do Stable
     */
    @Override
    public void run() {
        super.start();
        BettingCentreServerConnection connection;
        
        try {
            sSocket = new ServerSocket(port);
        } catch (IOException e) {
        }
        
        
        while (run) {
            try {
                cSocket = sSocket.accept();
                connection = new BettingCentreServerConnection(cSocket);
                connection.start();
            } catch (IOException e) {
            }

        }

    }

    /**
     * Função que termina o ciclo de "escuta" do servidor e termina o código a
     * correr pela thread.
     */
    public void close() {
        run = false;
        try {
            sSocket.close();
        } catch (IOException e) {
        }
    }
    
    /**
     * Classe privada para lançar thread para tratar msg recebida
     */
    private class BettingCentreServerConnection  extends Thread {

        private ObjectInputStream in = null;
        private ObjectOutputStream out = null;
        private Msg msgOut;
        private MsgType type;
        private Socket cSocket;
        private ArrayList<Object> param;

        /**
         * Construtor da classe interna para lançar thread que analisa o tipo de
         * mensagem e trata do encaminhamento da mesma.
         *
         * @param sock Mensagem recebida
         */
        public BettingCentreServerConnection (Socket sock) {
            cSocket = sock;
        }

        /**
         * Funcão que inicializa a thread da classe para tratar mensagem recebida.
         */
        @Override
        public void run() {
            try {
                in = new ObjectInputStream(cSocket.getInputStream());
                out = new ObjectOutputStream(cSocket.getOutputStream());
                
                msgOut = (Msg) in.readObject();
                type = msgOut.getType();
                param = msgOut.getParam();
                
                switch (type) {
                    case PLACEABET:
                        int spectatorID = (int) param.get(0);
                        int value = (int) param.get(1);
                        int horseID = (int) param.get(2);
                        bcSpectator.placeABet(spectatorID, value, horseID);
                        break;
                    case GOCOLLECTTHEGAINS:
                        int spectator = (int) param.get(0);
                        bcSpectator.goCollectTheGains(spectator);
                        break;
                    case RELAXABIT:
                        int spect = (int) param.get(0);
                        bcSpectator.relaxABit(spect);
                        break;
                    case ACCEPTTHEBETS:
                        bcBroker.acceptTheBets();
                        break;
                    case HONOURTHEBETS:
                        bcBroker.honourTheBets();
                        break;
                    case ARETHEREANYWINNERS:
                        ArrayList<Bet> winners = (ArrayList<Bet>) param.get(0);
                        bcBroker.areThereAnyWinners(winners);
                        break;
                    case ENTERTAINTHEGUESTS:
                        bcBroker.entertainTheGuests();
                        break;
                    case CLOSE:
                        close();
                        break;

                }
                
                // responde com a mesma mensagem que recebeu
                out.writeObject(msgOut);
                out.flush();
                out.close();
                in.close();
                cSocket.close();

            } catch (IOException e) {
            } catch (ClassNotFoundException ex) {
            }
        }
    }
    
}