package Clients;

import Broker.BrokerStates;
import GeneralRepository.*;
import Horse.HorseStates;
import Spectator.SpectatorStates;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe para envio de mensagens relacionados com o Repositorio, para o
 * servidor.
 *
 * @author Miguel Maia
 */
public class ClientRepository extends ClientSend implements IGeneralRepository {

    /**
     * Construtor da classe, que vai criar um Repositorio remoto. Recebe como
     * parametros um endereço IP address e uma porta port usada para o envio de
     * mensagens
     *
     * @param address Endereço IP
     * @param port Porta para envio das mensagens
     */
    public ClientRepository(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nCLIENT REPOSITORY\n");
    }

    /**
     * Método para chamar a função getHorseJump no servidor, recebendo do
     * servidor o valor de retorno da função. Esta função vai obter a passada de
     * um cavalo ID.
     *
     * @param ID ID do cavalo
     * @return msgReceived.getParam().get(0) Passada do cavalo
     */
    @Override
    public int getHorseSkills(int ID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        Msg msgReceived = (Msg) sendMessage(MsgType.GETHORSEAGILITY, param);
        return (int) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função setHorseJump no servidor, com o envio dos
     * parametros adequados. Esta função vai atualizar o valor da passada de um
     * cavalo
     *
     * @param ID ID do cavalo
     * @param move Passada do cavalo
     */
    @Override
    public void setHorseSkills(int ID, int move) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        param.add(move);
        sendMessage(MsgType.SETHORSEAGILITY, param);
    }

    /**
     * Método para chamar a função getnSpectates no servidor, recebendo do
     * servidor o valor de retorno da função. Esta função vai obter o numero de
     * espetadores
     *
     * @return msgReceived.getParam().get(0) Numero de espetadores
     */
    @Override
    public int getnSpectator() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETNSPECTATORS, null);
        return (int) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função setnSpectates no servidor, com o envio dos
     * parametros adequados. Esta função vai atualizar o numero de espetadores
     *
     * @param nSpectators Numero de espetadores
     */
    @Override
    public void setnSpectator(int nSpectators) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(nSpectators);
        sendMessage(MsgType.SETNSPECTATORS, param);
    }

    /**
     * Método para chamar a função getnHorses no servidor, recebendo do servidor
     * o valor de retorno da função. Esta função vai obter o numero de cavalos
     *
     * @return msgReceived.getParam().get(0) Numero de cavalos
     */
    @Override
    public int getnHorses() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETNHORSES, null);
        return (int) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função setnHorses no servidor, com o envio dos
     * parametros adequados. Esta função vai atualizar o numero de cavalos
     *
     * @param nHorses Numero de cavalos
     */
    @Override
    public void setnHorses(int nHorses) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(nHorses);
        sendMessage(MsgType.SETNHORSES, param);
    }

    /**
     * Método para chamar a função getnRaces no servidor, recebendo do servidor
     * o valor de retorno da função. Esta função vai obter o numero de corridas
     *
     * @return msgReceived.getParam().get(0) Numero de corridas
     */
    @Override
    public int getnRaces() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETNRACES, null);
        return (int) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função setnRaces no servidor, com o envio dos
     * parametros adequados. Esta função vai atualizar o numero de corridas
     *
     * @param nRaces Numero de corridas
     */
    @Override
    public void setnRaces(int nRaces) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(nRaces);
        sendMessage(MsgType.SETNRACES, param);
    }

    /**
     * Método para chamar a função getDistancia no servidor, recebendo do
     * servidor o valor de retorno da função. Esta função vai devolver a
     * distancia de uma pista
     *
     * @return msgReceived.getParam().get(0) Distania da pista
     */
    @Override
    public int getDistance() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETDISTANCIA, null);
        return (int) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função setDistancia no servidor, com o envio dos
     * parametros adequados. Esta função vai atualizar a distania da pista
     *
     * @param distancia Distância da pista
     */
    @Override
    public void setDistance(int distancia) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(distancia);
        sendMessage(MsgType.SETDISTANCIA, param);
    }

    /**
     * Método para chamar a função getHorseWinnerID no servidor, recebendo do
     * servidor o valor de retorno da função. Esta função vai obter o horseID do
     * cavalo vencedor
     *
     * @return msgReceived.getParam().get(0) HorseID do cavalo vencedor
     */
    @Override
    public int getHorseWinnerID() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETHORSEWINNERID, null);
        return (int) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função setHorseWinnerID no servidor, com o envio dos
     * parametros adequados. Esta função vai atualizar o cavalo vencedor
     *
     * @param horseID ID do cavalo
     */
    @Override
    public void setHorseWinnerID(int horseID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        sendMessage(MsgType.SETHORSEWINNERID, param);
    }

    /**
     * Método para chamar a função getnWinners no servidor, recebendo do
     * servidor o valor de retorno da função. Esta função vai obter o numero de
     * apostadores vencedores de uma corrida
     *
     * @return msgReceived.getParam().get(0) Numero de vencedores
     */
    @Override
    public int getnWinners() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETNWINNERS, null);
        return (int) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função setnWinners no servidor, com o envio dos
     * parametros adequados. Esta função vai atualizar o numero de apostadores
     * vencedores de uma corrida
     *
     * @param size Numero de vencedores
     */
    @Override
    public void setnWinners(int size) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(size);
        sendMessage(MsgType.SETNWINNERS, param);
    }

    /**
     * Método para chamar a função getCurrentRace no servidor, recebendo do
     * servidor o valor de retorno da função. Esta função vai obter a corrida
     * atual
     *
     * @return msgReceived.getParam().get(0) Corrida atual
     */
    @Override
    public int getCurrentRace() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETCURRENTRACE, null);
        return (int) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função setCurrentRace no servidor, com o envio dos
     * parametros adequados. Esta função vai atualizar a corrida atual
     *
     * @param race Corrida atual
     */
    @Override
    public void setActualRace(int race) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(race);
        sendMessage(MsgType.SETCURRENTRACE, param);
    }
    
    
    /**
     * Método para chamar a função getBetsPerPunter no servidor, recebendo do
     * servidor o valor de retorno da função. Esta função vai obter uma lista de
     * apostas por apostador
     *
     * 
     */
    /*
    @Override
    public HashMap<Integer, Bet> getBetsPerSpectator() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETBETSPERPUNTER, null);
        return (HashMap<Integer, Bet>) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função setBetsPerPunter no servidor, com o envio dos
     * parametros adequados. Esta função vai atualizar uma lista de apostas bet
     * de um apostador ID
     *
     * @param ID ID do apostador
     * @param bet Lista de apostas
     */
    @Override
    public void setBetsPerSpectator(int ID, Bet bet) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        param.add(bet);
        sendMessage(MsgType.SETBETSPERSPECTATOR, param);
    }

    /**
     * Método para chamar a função getBetsByPunterID no servidor, recebendo do
     * servidor o valor de retorno da função. Esta função vai obter uma
     * determinada aposta de um apostador
     *
     * @param ID ID do apostador
     *
     * @return msgReceived.getParam().get(0) Aposta feita pelo apostador
     * indicado
     */
    @Override
    public Bet getBetsPerSpectator(int ID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        Msg msgReceived = (Msg) sendMessage(MsgType.GETBETBYSPECTATORID, param);
        return (Bet) msgReceived.getParam().get(0);
    }

    /**
     * Método para chamar a função resetBetsPerID no servidor. Esta função vai
     * fazer reset às apostas dos apostadores
     */
    /*
    @Override
    public void resetBetsPerID() {
        sendMessage(MsgType.RESETBETSPERID, null);
    }
    */
    
    /**
     * Método para chamar a função getHorsesPositions no servidor, recebendo do
     * servidor o valor de retorno da função. Esta função vai obter uma lista de
     * posições de um cavalo numa corrida
     *
     * @return msgReceived.getParam().get(0) Lista de posições do cavalo
     */
    /*
    @Override
    public HashMap<Integer, Integer> getHorsesPositions() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETHORSESPOSITIONS, null);
        return (HashMap<Integer, Integer>) msgReceived.getParam().get(0);
    }
    */
    /**
     * Método para chamar a função setHorsesPositions no servidor, com o envio
     * dos parametros adequados. Esta função vai atualizar uma lista de posições
     * de um cavalo numa corrida
     *
     * @param id - id do cavalo
     * @param position - posição do cavalo
     */
    @Override
    public void sethorsePositions(int id, int position) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(position);
        sendMessage(MsgType.SETHORSESPOSITIONS, param);
    }    

    @Override
    public int gethorsePosition(int id) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(id);
        Msg msgReceived = (Msg) sendMessage(MsgType.GETHORSESPOSITION, param);
        return (int) msgReceived.getParam().get(0);  
    }

    @Override
    public void setArrayPosition(HashMap<Integer, Integer> pos) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(pos);
        sendMessage(MsgType.SETARRAYPOSITION, param);
    }

    @Override
    public void setCount(int horse, int count) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setHorseState(int horseID, HorseStates state, int move) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        param.add(state);
        param.add(move);
        sendMessage(MsgType.SETHORSESTATE, param);
    }

    @Override
    public void setSpectatorState(int spectatorID, SpectatorStates state) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(spectatorID);
        param.add(state);
        sendMessage(MsgType.SETSPECTATORSTATE, param);
    }

    @Override
    public void setSpectatorMoney(int spectatorID, int money) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(spectatorID);
        param.add(money);
        sendMessage(MsgType.SETSPECTATORMONEY, param);
    }

    @Override
    public void setSpectatorBet(int spectatorID, int bet) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(spectatorID);
        param.add(bet);
        sendMessage(MsgType.SETSPECTATORBET, param);
    }

    @Override
    public void setBrokerState(BrokerStates state) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(state);
        sendMessage(MsgType.SETBROKERSTATE, param);
    }
    
    /**
     * Método para terminar a ligação e fechar a classe
     */
    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }
}
