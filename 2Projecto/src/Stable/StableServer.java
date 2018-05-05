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
 * Servidor Stable
 *
 * @author cristianacarvalho
 */
public class StableServer extends Thread {

    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IStable st;
    

    /**
     *
     * @param st Instancia da interface IStable
     * @param port Porta onde o servidor fica a "escuta" das mensagens
     */
    public StableServer(IStable st, int port) {
        this.st = st;
        this.port = port;
        System.out.printf("\nSTABLE SERVER\n");
    }

    /**
     * Funcão que inicializa a thread do Stable.
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
     * Função que termina o ciclo do servidor e termina o código a
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
         *
         * @param sock Mensagem recebida
         */
        public StableServerConnection(Socket sock) {
            cSocket = sock;
        }

        /**
         * Inicializa a thread da classe para tratar mensagem recebida.
         */
        @Override
        public void run() {
            try {
                in = new ObjectInputStream(cSocket.getInputStream());
                out = new ObjectOutputStream(cSocket.getOutputStream());
                
                msgOut = (Msg) in.readObject();
                type = msgOut.getType();
                param = msgOut.getParam();
                System.err.print("\nSTABLE SERVER RECEBEU UMA MENSAGEM COM TYPE: " + type.name() + "\nparam" + param.toString());

                switch (type) {
                    case PROCEEDTOSTABLE:
                        int horseID = (int) param.get(0);
                        st.proceedToStable(horseID);
                        break;
                    case SUMMONHORSESTOPADDOCK:
                        st.summonHorsesToPaddock();
                        break;
                    case CLOSE:
                        close();
                        break;
                }
                
                
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
