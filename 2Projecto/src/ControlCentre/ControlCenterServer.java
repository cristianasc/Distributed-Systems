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
 * Servidor para recepção das mensagens enviadas pelos clientes
 * (Cavalo,Apostador,Manager) relacionadas com o Centro de Controlo.
 *
 * @author Ricardo Martins
 */
public class ControlCenterServer extends Thread {

    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IControlCenter cc;

    /**
     * Construtor da classe servidor para centro de controlo, recebe como
     * parâmetro uma instancia da interface ControlCenter cc, e uma porta por
     * onde o servidor vai receber as mensagens
     *
     * @param cc Instância da interface IControlCenter, que toma o valor
     * ControlCenterLocal.
     * @param port Porta onde o servidor fica a "escuta" das mensagens.
     */
    public ControlCenterServer(IControlCenter cc, int port) {
        this.cc = cc;
        this.port = port;
        System.out.printf("\nCRIOU CONTROLCENTER SERVER\n");
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
            try {
                cSocket = sSocket.accept();
                new ControlCenterServer.ControlCenterConnection(cSocket).start();
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
                int horseID, punterID;
                ArrayList<Object> tmp = new ArrayList<>();
                System.out.print("\nCONTROL SERVER RECEBEU UMA MENSAGEM COM TYPE: " + type.name() + "param" + param.toString());
                switch (type) {
                    case GETRACEFINISHED:
                        boolean received = cc.getRaceFinished();
                        tmp.add(received);
                        break;
                    case SETRACEFINISHED:
                        // bc.setRaceFinished();
                        break;
                    case WAITFORNEXTRACE:
                        punterID = (int) param.get(0);
                        cc.waitForNextRace(punterID);
                        break;
                    case CALLPUNTERS:
                        cc.callPunters();
                        break;
                    case WATCHTHERACE:
                        punterID = (int) param.get(0);
                        cc.watchTheRace(punterID);
                        break;
                    case REPORTRESULTS:
                        ArrayList<Bet> lista = (ArrayList<Bet>) param.get(0);
                        cc.reportResults(lista);
                        break;
                    case HAVEIWON:
                        punterID = (int) param.get(0);
                        boolean tmpbool = cc.haveIWon(punterID);
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
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
