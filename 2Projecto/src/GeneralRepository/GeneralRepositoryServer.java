package GeneralRepository;

import Clients.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Servidor para recepção das mensagens enviadas pelos clientes
 * (Cavalo,Apostador,Manager) relacionadas com o Repositorio
 *
 * @author Miguel Maia
 */
public class GeneralRepositoryServer extends Thread {

    private ServerSocket sSocket = null;
    private Socket cSocket = null;
    private int port;
    private boolean run = true;
    private IGeneralRepository rp;

    /**
     * Construtor da classe servidor para o Repositorio, recebe como parâmetro
     * uma instancia da interface Repositorio rp, e uma porta por onde o
     * servidor vai receber as mensagens
     *
     * @param rp Instancia da interface IRepository, que toma o valor
     * RepositoryLocal
     * @param port Porta onde o servidor fica a "escuta" das mensagens
     */
    public GeneralRepositoryServer(IGeneralRepository rp, int port) {
        this.rp = rp;
        this.port = port;
        System.out.printf("\nGENERAL REPOSITORY SERVER\n");
    }

    /**
     * Funcão que inicializa a thread do Repositorio.
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
                System.out.printf("\nGENERAL REPOSITORY SERVER LISTENING..\n");
            try {
                cSocket = sSocket.accept();
                new GeneralRepositoryServer.RepositoryConnection(cSocket).start();
            } catch (IOException e) {
            }

        }
        System.out.printf("\nGENERAL REPOSITORY SERVER OVER\n");
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
    private class RepositoryConnection extends Thread {

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
        public RepositoryConnection(Socket sock) {
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
                int value;
                int nspecs, nhorses, nRaces, distancia, winnerhorseID;
                ArrayList<Object> tmp = new ArrayList<>();
                System.out.print("\nREPOSITORY SERVER RECEBEU UMA MENSAGEM COM TYPE: " + type.name());
                switch (type) {
                    case GETHORSEAGILITY:
                        horseID = (int) param.get(0);
                        int jump = rp.getHorseSkills(horseID);
                        tmp.add(jump);
                        break;
                    case SETHORSEAGILITY:
                        horseID = (int) param.get(0);
                        value = (int) param.get(1);
                        rp.setHorseSkills(horseID, value);
                        break;
                    case GETNSPECTATORS:
                        int specs = rp.getnSpectator();
                        tmp.add(specs);
                        break;
                    case SETNSPECTATORS:
                        nspecs = (int) param.get(0);
                        rp.setnSpectator(nspecs);
                        break;
                    case GETNHORSES:
                        int horses = rp.getnHorses();
                        tmp.add(horses);
                        break;
                    case SETNHORSES:
                        nhorses = (int) param.get(0);
                        rp.setnHorses(nhorses);
                        break;
                    case GETNRACES:
                        int races = rp.getnRaces();
                        tmp.add(races);
                        break;
                    case SETNRACES:
                        nRaces = (int) param.get(0);
                        rp.setnRaces(nRaces);
                        break;
                    case GETDISTANCIA:
                        int dist = rp.getDistance();
                        tmp.add(dist);
                        break;
                    case SETDISTANCIA:
                        distancia = (int) param.get(0);
                        rp.setDistance(distancia);
                        break;
                    case GETHORSEWINNERID:
                        int horseWin = rp.getHorseWinnerID();
                        tmp.add(horseWin);
                        break;
                    case SETHORSEWINNERID:
                        winnerhorseID = (int) param.get(0);
                        rp.setHorseWinnerID(winnerhorseID);
                        break;
                    case GETNWINNERS:
                        int numwinners = rp.getnWinners();
                        tmp.add(numwinners);
                        break;
                    case SETNWINNERS:
                        int size = (int) param.get(0);
                        rp.setnWinners(size);
                        break;
                    case GETCURRENTRACE:
                        int raceId = rp.getCurrentRace();
                        tmp.add(raceId);
                        break;
                    case SETCURRENTRACE:
                        int raceNum = (int) param.get(1);
                        rp.setActualRace(raceNum);
                        break;
                        /*
                    case GETBETSPERPUNTER:
                        HashMap<Integer, Bet> mapa = rp.getBetsPerPunter();
                        tmp.add(mapa);
                        break;
                    */
                    case SETBETSPERPUNTER:
                        int punterID = (int) param.get(0);
                        Bet bet = (Bet) param.get(1);
                        rp.setBetsPerSpectator(punterID, bet);
                        break;
                    case GETBETBYPUNTERID:
                        punterID = (int) param.get(0);
                        Bet betX = rp.getBetsPerSpectator(punterID);
                        tmp.add(betX);
                        break;
                    case GETHORSESPOSITIONS:
                        int post = rp.gethorsePosition((int) param.get(0));
                        tmp.add(post);
                        break;
                    case SETHORSESPOSITIONS:
                        horseID = (int) param.get(0);
                        int pos = (int) param.get(1);
                        rp.sethorsePositions(horseID, pos);
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
