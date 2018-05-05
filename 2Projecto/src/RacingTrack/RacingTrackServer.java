package RacingTrack;

import Clients.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Servidor RacingTrack.
 *
 * @author Miguel Maia
 */
public class RacingTrackServer extends Thread {

    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IRacingTrack rt;

    /**
     * 
     * @param rt Instância da interface IRacingTrack
     * @param port Porta onde o servidor fica a "escuta" das mensagens 
     */
    public RacingTrackServer(IRacingTrack rt, int port) {
        this.rt = rt;
        this.port = port;
        System.out.printf("\nRACINGTRACK SERVER\n");
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
            System.out.printf("\nRACINGTRACK SERVER LISTENING..\n");
            try {
                cSocket = sSocket.accept();
                new RacingTrackServer.RacingTrackConnection(cSocket).start();
            } catch (IOException e) {
            }

        }
        System.out.printf("\nRACINGTRACK SERVER OVER\n");

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
    private class RacingTrackConnection extends Thread {

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
        public RacingTrackConnection(Socket sock) {
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
                int horseID;
                ArrayList<Object> tmp = new ArrayList<>();
                System.err.print("\nRACINGTRACK SERVER RECEBEU UMA MENSAGEM COM TYPE: " + type.name() + "param" + param.toString());
                switch (type) {
                    case PROCEEDTOSTARTLINE:
                        horseID = (int) param.get(0);
                        rt.proceedToStartLine(horseID);
                        break;
                    case HASFINISHLINEBEENCROSSED:
                        horseID = (int) param.get(0);
                        boolean crossed = rt.hasFinishLineBeenCrossed(horseID);
                        tmp.add(crossed);
                        break;
                    case STARTTHERACE:
                        rt.startTheRace();
                        break;
                    case MAKEAMOVE:
                        horseID = (int) param.get(0);
                        int maxJump = (int) param.get(1);
                        int count = (int) param.get(2);
                        rt.makeAMove(horseID, maxJump,count);
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
