package RacingTrack;

import Clients.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Servidor para recepção das mensagens enviadas pelos clientes
 * (Cavalo,Apostador,Manager) relacionadas com o RacingTrack
 *
 * @author Miguel Maia
 */
public class RacingTrackServer extends Thread {

    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IRacingTrack_Horses rtH;
    private IRacingTrack_Broker rtB;

    /**
     * Construtor da classe servidor para o RacingTrack, recebe como parâmetro
     * uma instancia da interface racingTrack rt, e uma porta por onde o
     * servidor vai receber as mensagens
     *
     * @param rt Instância da interface IRacingTrack, que toma o valor
     * RacingTrackLocal.
     * @param port Porta onde o servidor fica a "escuta" das mensagens
     */
    public RacingTrackServer(IRacingTrack_Broker rtB, IRacingTrack_Horses rtH, int port) {
        this.rtB = rtB;
        this.rtH = rtH;
        this.port = port;
        System.out.printf("\nCRIOU RACINGTRACK SERVER\n");
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
            try {
                cSocket = sSocket.accept();
                new RacingTrackServer.RacingTrackConnection(cSocket).start();
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
    private class RacingTrackConnection extends Thread {

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
        public RacingTrackConnection(Socket sock) {
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
                int horseID;
                ArrayList<Object> tmp = new ArrayList<>();
                System.out.print("\nRACINGTRACK SERVER RECEBEU UMA MENSAGEM COM TYPE: " + type.name() + "param" + param.toString());
                switch (type) {
                    case PROCEEDTOSTARTLINE:
                        horseID = (int) param.get(0);
                        rtH.proceedToStartLine(horseID);
                        break;
                    case HASFINISHLINEBEENCROSSED:
                        horseID = (int) param.get(0);
                        boolean crossed = rtH.hasFinishLineBeenCrossed(horseID);
                        tmp.add(crossed);  //PERGUNTAR AO LUCONAS SE ISTO FICA SEMPRE NO INDICE 0
                        break;
                    case STARTTHERACE:
                        rtB.startTheRace();
                        break;
                    case MAKEAMOVE:
                        horseID = (int) param.get(0);
                        int maxJump = (int) param.get(1);
                        int count = (int) param.get(2);
                        rtH.makeAMove(horseID, maxJump,count);
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
