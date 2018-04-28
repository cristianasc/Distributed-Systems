package Stable;

import Clients.Msg;
import Clients.MsgType;
import static Clients.MsgType.CLOSE;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Servidor para recepção das mensagens enviadas pelos clientes
 * (Cavalo,Espectador,Broker) relacionadas com o Stable
 *
 * @author cristianacarvalho
 */
public class StableServer extends Thread {

    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IStable_Broker stBroker;
    private IStable_Horses stHorses;
    

    /**
     * Construtor da classe servidor para o Stable, recebe como parâmetro uma
     * instancia da interface IStable, IStable_Broker e IStable_Horses, e uma porta por onde o servidor vai
     * receber as mensagens
     *
     * @param stBroker Instancia da interface IStable_Broker
     * @param stHorses Instancia da interface IStable_Horses
     * @param port Porta onde o servidor fica a "escuta" das mensagens
     */
    public StableServer(IStable_Broker stBroker, IStable_Horses stHorses, int port) {
        this.stBroker = stBroker;
        this.stHorses = stHorses;
        this.port = port;
        System.out.printf("\nSTABLE SERVER\n");
    }

    /**
     * Funcão que inicializa a thread do Stable
     */
    @Override
    public void run() {
        super.run();
        StableServerConnection connection;
        
        try {
            sSocket = new ServerSocket(port);
        } catch (IOException e) {
        }
        
        
        while (run) {
            System.out.printf("\nSTABLE SERVER LISTENING..\n");
            try {
                cSocket = sSocket.accept();
                connection = new StableServerConnection(cSocket);
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
    private class StableServerConnection extends Thread {

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
        public StableServerConnection(Socket sock) {
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
                
                switch (type) {
                    case SUMMONHORSESTOPADDOCK:
                        stBroker.summonHorsesToPaddock();
                        break;
                    case PROCEEDTOSTABLE:
                        int horseID = (int) param.get(0);
                        stHorses.proceedToStable(horseID);
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
