package ControlCentre;

import GeneralRepository.*;
import Clients.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public class ControlCenterServer extends Thread {

    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IControlCentre_Horses ccH;
    private IControlCentre_Broker ccB;
    private IControlCentre_Spectator ccS;

    /**
     * Construtor da classe servidor para centro de controlo, recebe como
     * parâmetro uma instancia da interface ControlCenter cc, e uma porta por
     * onde o servidor vai receber as mensagens
     *
     * @param ccH Instância da interface IControlCentre_Horses.
     * @param ccB Instância da interface IControlCentre_Broker.
     * @param ccS Instância da interface IControlCentre_Spectator.
     * @param port Porta onde o servidor fica a "escuta" das mensagens.
     */
    public ControlCenterServer(IControlCentre_Horses ccH,IControlCentre_Broker ccB,IControlCentre_Spectator ccS, int port) {
        this.ccH = ccH;
        this.ccB = ccB;
        this.ccS = ccS;
        this.port = port;
        System.out.printf("\nCONTROLCENTER SERVER\n");
    }

    /**
     * Funcão que inicializa a thread do do Servidor.
     */
    @Override
    public void run() {
        super.run();
        try {
            sSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (run) {
            System.out.printf("\nCONTROLCENTRE SERVER LISTENING..\n");
            try {
                cSocket = sSocket.accept();
                new ControlCenterServer.ControlCenterConnection(cSocket).start();
            } catch (IOException e) {
            }
        }
        System.out.printf("\nCONTROLCENTRE SERVER OVER\n");
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
            System.err.println("IOException");
        }
    }

    /**
     * Classe privada para lançar thread para tratar msg recebida
     */
    private class ControlCenterConnection extends Thread {

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
        public ControlCenterConnection(Socket sock) {
            cSocket = sock;
        }

        /**
         * Funcão que inicializa a thread da classe para tratar mensagem
         * recebida.
         */
        @Override
        public void run() {
            try {
                in = new ObjectInputStream(cSocket.getInputStream());
                out = new ObjectOutputStream(cSocket.getOutputStream());
                
                msgOut = (Msg) in.readObject();
                type = msgOut.getType();
                param = msgOut.getParam();
                
                int horseID, spectatorID;
                ArrayList<Object> tmp = new ArrayList<>();
                System.out.print("\nCONTROLCENTRE SERVER RECEBEU UMA MENSAGEM COM TYPE: " + type.name());
                switch (type) {
                    case REPORTRESULTS:
                        ArrayList<Bet> lista = (ArrayList<Bet>) param.get(0);
                        ccB.reportResults(lista);
                        break;
                    case SUMMONHORSESTOPADDOCKCC:
                        ccB.summonHorsesToPaddock();
                        break;
                    case GETWINNERS:
                        ArrayList<Bet> winners = ccB.getWinners();
                        tmp.add(winners);
                        break;     
                    case PROCEEDTOPADDOCK:
                        horseID = (int) param.get(0);
                        ccH.proceedToPaddock(horseID);
                        break;
                    case GOWATCHTHERACE:
                        spectatorID = (int) param.get(0);
                        ccS.goWatchTheRace(spectatorID);
                        break;
                    case WAITFORNEXTRACE:
                        spectatorID = (int) param.get(0);
                        ccS.waitForTheNextRace(spectatorID);
                        break;
                    case HAVEIWON:
                        spectatorID = (int) param.get(0);
                        boolean tmpbool = ccS.haveIWon(spectatorID);
                        tmp.add(tmpbool);
                        break;
                    case CLOSE:
                        close();
                        break;
                }
                
                // responde com msg Out ou in          
                msgOut.setType(type);
                msgOut.setParam(tmp);

                out.writeObject(msgOut);
                out.flush();
                out.close();
                in.close();
                cSocket.close();

            } catch (IOException e) {
                System.err.println("IOException ");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.err.println("ClassNotFoundException CC");
                e.printStackTrace();
            }
        }
    }
}
