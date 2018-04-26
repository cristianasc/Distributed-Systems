package Clients;

import java.net.InetAddress;
import java.util.ArrayList;
import ControlCentre.*;
import GeneralRepository.*;

/**
 * Classe para envio de mensagens relacionados com o Centro de controlo, para o
 * servidor.
 *
 * @author Miguel Maia
 *
 */
public class ClientControlCenter extends ClientSend implements IControlCentre_Broker, IControlCentre_Spectator,IControlCentre_Horses {

    /**
     * Construtor da classe, que vai criar um centro de controlo remoto. Recebe
     * como parametros um endereço IP address e uma porta port usada para o
     * envio de mensagens
     *
     * @param address Endereço IP
     * @param port Porta para envio das mensagens
     */
    public ClientControlCenter(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nCRIOU CONTROLCENTER REMOTE\n");
    }

    /**
     * Método para chamar a função watchTeerace no servidor, com o envio dos
     * parametros adequados. Esta função diz respeito ao comportamento de um
     * apostador Punter_ID durante uma corrida.
     *
     * @param Punter_ID ID do apostador
     */
    @Override
    public void goWatchTheRace(int Punter_ID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(Punter_ID);
        sendMessage(MsgType.WATCHTHERACE, param);
    }

    /**
     * Método para chamar a função reportResults no servidor, com o envio dos
     * parametros adequados. Esta função vai acordar os apostadores que estão a
     * visualizar uma corrida.
     *
     * @param betlist Lista com os apostadores vencedores
     */
    @Override
    public void reportResults(ArrayList<Bet> betlist) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(betlist);
        sendMessage(MsgType.REPORTRESULTS, param);
    }

    /**
     * Método para chamar a função haveIWon no servidor, recebendo do servidor o
     * valor de retorno da função. Esta função vai verificar se o apostador
     * punterID ganhou a corrida.
     *
     * @param punterID ID do apostador
     *
     * @return Boolean indicando se o apostador punterID ganhou a corrida
     */
    @Override
    public boolean haveIWon(int punterID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(punterID);
        Msg msgReceive = (Msg) sendMessage(MsgType.HAVEIWON, param);
        return (boolean) msgReceive.getParam().get(0);
    }

    /**
     * Método para terminar a ligação e fechar a classe
     */
    @Override
    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }

    @Override
    public void summonHorsesToPaddock() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Bet> getWinners() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void proceedToPaddock(int HorseID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void waitForTheNextRace(int spectatorID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
