/**
 *
 */
package Stable;

import Utils.Msg;
import Utils.MsgType;
import static Utils.MsgType.CLOSE;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Servidor para recepção das mensagens enviadas pelos clientes
 * (Cavalo,Apostador,Manager) relacionadas com o Stable
 *
 * @author Tiago Marques
 */
public class StableServer extends Thread {

    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IStable stable;

    /**
     * Construtor da classe servidor para o Stable, recebe como parâmetro uma
     * instancia da interface stable, e uma porta por onde o servidor vai
     * receber as mensagens
     *
     * @param stable Instancia da interface IStable que toma o valor de StableLocal
     * @param port Porta onde o servidor fica a "escuta" das mensagens
     */
    public StableServer(IStable stable, int port) {
        this.stable = stable;
        this.port = port;
        System.out.printf("\nCRIOU STABLE SERVER\n");
    }

    /**
     * Funcão que inicializa a thread do Stable
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
                new StableServerConnection(cSocket).start();
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
                //msgOut = new MsgOut();
                type = msgOut.getType();
                param = msgOut.getParam();
                switch (type) {
                    case CALLTOPADDOCK:
                        stable.callToPaddock();
                        break;
                    case PROCEEDTOSTABLE:
                        int horseID = (int) param.get(0);
                        stable.proceedToStable(horseID);
                        break;
                    case CLOSE:
                        close();
                        break;

                }
                
                // responde com a msma mensagem que recebeu
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
