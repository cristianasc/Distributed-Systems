package Paddock;

import Clients.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * Servidor para recepção das mensagens enviadas pelos clientes
 * (Cavalo,Espectador,Broker) relacionadas com o Stable
 *
 * @author cristianacarvalho
 */
public class PaddockServer extends Thread {
    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IPaddock_Horses pdHorses;
    private IPaddock_Spectator pdSpectator;
    
    
    /**
     * Construtor da classe servidor para o Paddock, recebe como parâmetro uma
     * instancia da interface IPaddock_Horses e IPaddock_Spectator, e uma porta por onde o servidor vai
     * receber as mensagens
     *
     * @param pdSpectator Instancia da interface IPaddock_Horses
     * @param pdHorses Instancia da interface IPaddock_Spectator
     * @param port Porta onde o servidor fica a "escuta" das mensagens
     */
    public PaddockServer(IPaddock_Spectator pdSpectator, IPaddock_Horses pdHorses, int port) {
        this.pdSpectator = pdSpectator;
        this.pdHorses = pdHorses;
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
    private class PaddockServerConnection extends Thread {

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
        public PaddockServerConnection(Socket sock) {
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
                    case PROCEEDTOSTARTLINE:
                        pdHorses.proceedToStartLine();
                        break;
                    case PROCEEDTOPADDOCK:
                        int horseID = (int) param.get(0);
                        pdHorses.proceedToPaddock(horseID);
                        break;
                    case GOCHECKHORSES:
                        int spectatorID = (int) param.get(0);
                        pdSpectator.goCheckHorses(spectatorID);
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
