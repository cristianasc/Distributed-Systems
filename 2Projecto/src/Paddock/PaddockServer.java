package Paddock;

import Clients.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * Servidor Paddock
 *
 * @author cristianacarvalho
 */
public class PaddockServer extends Thread {
    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IPaddock pd;
    
    
    /**
     * @param pd Instancia da interface IPaddock
     * @param port Porta onde o servidor fica a "escuta" das mensagens
     */
    public PaddockServer(IPaddock pd, int port) {
        this.pd = pd;
        this.port = port;
        System.out.printf("\nPADDOCK SERVER\n");
    }

    /**
     * Funcão que inicializa a thread do Paddock
     */
    @Override
    public void run() {
        super.run();
        PaddockServerConnection connection;
        
        try {
            sSocket = new ServerSocket(port);
        } catch (IOException e) {
        }
        
        
        while (run) {
            System.out.printf("\nPADDOCK SERVER LISTENING..\n");
            try {
                cSocket = sSocket.accept();
                connection = new PaddockServerConnection(cSocket);
                connection.start();
            } catch (IOException e) {
            }

        }
        System.out.printf("\nPADDOCK SERVER OVER\n");

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
    private class PaddockServerConnection extends Thread {

        private ObjectInputStream in = null;
        private ObjectOutputStream out = null;
        private Msg msgOut;
        private MsgType type;
        private Socket cSocket;
        private ArrayList<Object> param;

        /**
         * @param sock Mensagem recebida
         */
        public PaddockServerConnection(Socket sock) {
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
                System.out.print("\nPADDOCK SERVER RECEBEU UMA MENSAGEM COM TYPE: " + type.name());
                switch (type) {
                    case PROCEEDTOSTARTLINE:
                        pd.proceedToStartLine();
                        break;
                    case PROCEEDTOPADDOCK:
                        int horseID = (int) param.get(0);
                        pd.proceedToPaddock(horseID);
                        break;
                    case GOCHECKHORSES:
                        int spectatorID = (int) param.get(0);
                        pd.goCheckHorses(spectatorID);
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
