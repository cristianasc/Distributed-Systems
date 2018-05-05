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
    private IBettingCentre bc;
    

    /**
     * Construtor da classe servidor para o Betting Center, recebe como parâmetro uma
 instancia da interface IBettingCentre, IBettingCentre_Spectator e uma porta
 por onde o servidor vai receber as mensagens
     *
     * @param bcBroker Instancia da interface IBettingCentre
     * @param port Porta onde o servidor fica a "escuta" das mensagens
     */
    public BettingCentreServer(IBettingCentre bc, int port) {
        this.bc = bc;
        this.port = port;
        System.out.printf("\nBETTING CENTER SERVER\n");
    }

    /**
     * Funcão que inicializa a thread do Betting Center
     */
    @Override
    public void run() {
        super.run();
        BettingCentreServerConnection connection;
        
        try {
            sSocket = new ServerSocket(port);
        } catch (IOException e) {
        }
        
        
        while (run) {
            System.out.printf("\nBETTINGCENTRE SERVER LISTENING..\n");
            try {
                cSocket = sSocket.accept();
                connection = new BettingCentreServerConnection(cSocket);
                connection.start();
            } catch (IOException e) {
            }

        }
        System.out.printf("\nBETTING CENTRE SERVER OVER\n");

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
                ArrayList<Object> tmp = new ArrayList<>();
                System.out.print("\nBETTINGCENTRE SERVER RECEBEU UMA MENSAGEM COM TYPE: " + type.name());
                switch (type) {
                    case PLACEABET:
                        int spectatorID = (int) param.get(0);
                        int value = (int) param.get(1);
                        int horseID = (int) param.get(2);
                        bc.placeABet(spectatorID, value, horseID);
                        break;
                    case GOCOLLECTTHEGAINS:
                        int spectator = (int) param.get(0);
                        bc.goCollectTheGains(spectator);
                        break;
                    case RELAXABIT:
                        int spect = (int) param.get(0);
                        bc.relaxABit(spect);
                        break;
                    case ACCEPTTHEBETS:
                        Bet bet = bc.acceptTheBets();
                        tmp.add(bet);
                        break;
                    case HONOURTHEBETS:
                        bc.honourTheBets();
                        break;
                    case ARETHEREANYWINNERS:
                        ArrayList<Bet> winners = (ArrayList<Bet>) param.get(0);
                        boolean Bwinners = bc.areThereAnyWinners(winners);
                        tmp.add(Bwinners);
                        break;
                    case ENTERTAINTHEGUESTS:
                        bc.entertainTheGuests();
                        break;
                    case CLOSE:
                        close();
                        break;
                }
                       
                msgOut.setType(type);
                msgOut.setParam(tmp);
                
                out.writeObject(msgOut);
                out.flush();
                out.close();
                in.close();
                cSocket.close();

            } catch (IOException e) {
                System.err.println("IOException BC");
            } catch (ClassNotFoundException ex) {
                System.err.println("ClassNotFoundException BC");
            }
        }
    }
    
}
