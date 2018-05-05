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
    private IControlCentre cc;

    /**
     * @param cc Instância da interface IControlCentre.
     * @param port Porta onde o servidor fica a "escuta" das mensagens.
     */
    public ControlCenterServer(IControlCentre cc, int port) {
        this.cc = cc;
        this.port = port;
        System.out.printf("\nCONTROLCENTER SERVER\n");
    }

    /**
     * Funcão que inicializa a thread do Servidor.
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
     * Função que termina o ciclo do servidor e termina o código a
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
         * @param sock Mensagem recebida
         */
        public ControlCenterConnection(Socket sock) {
            cSocket = sock;
        }

        /**
         * Inicializa a thread da classe para tratar mensagem
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
                        cc.reportResults(lista);
                        break;
                    case SUMMONHORSESTOPADDOCKCC:
                        cc.summonHorsesToPaddock();
                        break;
                    case GETWINNERS:
                        ArrayList<Bet> winners = cc.getWinners();
                        tmp.add(winners);
                        break;     
                    case PROCEEDTOPADDOCK:
                        horseID = (int) param.get(0);
                        cc.proceedToPaddock(horseID);
                        break;
                    case GOWATCHTHERACE:
                        spectatorID = (int) param.get(0);
                        cc.goWatchTheRace(spectatorID);
                        break;
                    case WAITFORNEXTRACE:
                        spectatorID = (int) param.get(0);
                        cc.waitForTheNextRace(spectatorID);
                        break;
                    case HAVEIWON:
                        spectatorID = (int) param.get(0);
                        boolean tmpbool = cc.haveIWon(spectatorID);
                        tmp.add(tmpbool);
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
            } catch (ClassNotFoundException e) {
            }
        }
    }
}
