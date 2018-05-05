/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import RacingTrack.*;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Classe para envio de mensagens relacionados com o RacingTrack, para o
 * servidor.
 *
 * @author Miguel Maia
 */
public class ClientRacingTrack extends ClientSend implements IRacingTrack {

    /**
     * Construtor da classe, que vai criar um RacingTrack remoto. Recebe como
     * parametros um endereço IP address e uma porta port usada para o envio de
     * mensagens
     *
     * @param address Endereço IP
     * @param port Porta para envio das mensagens
     */
    public ClientRacingTrack(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nRACINGTRACK CLIENT \n");
    }

    /**
     * Método para chamar a função proceedToStartLine no servidor, com o envio
     * dos parametros adequados. Esta função simula o comportamento de um cavalo
     * horseID na linha de partida.
     *
     * @param horseID ID do cavalo
     */
    @Override
    public void proceedToStartLine(int horseID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        sendMessage(MsgType.PROCEEDTOSTARTLINE, param);
    }

    /**
     * Método para chamar a função hasFinishLineBeenCrossed no servidor,
     * recebendo do servidor o valor de retorno da função. Esta função indica se
     * um cavalo ID atravessou a linha da meta.
     *
     * @param ID ID do cavalo
     * @return Boolean a indicar se o cavalo já atravessou ou não a linha da
     * meta
     */
    @Override
    public boolean hasFinishLineBeenCrossed(int ID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        Msg msgReceived = (Msg) sendMessage(MsgType.HASFINISHLINEBEENCROSSED, param);
        return (boolean) msgReceived.getParam().get(0);  //garantir que boolean de resposta vem na posição 0
    }

    /**
     * Método para chamar a função startRace no servidor. Esta função serve para
     * inicializar a corrida, estando recolhidas as apostas dos espetadores.
     */
    @Override
    public void startTheRace() {
        sendMessage(MsgType.STARTTHERACE, null);
    }

    /**
     * Método para chamar a função makeAMove no servidor, recebendo do servidor
     * o valor de retorno da função. Esta função vai calcular um movimento de um
     * cavalo numa corrida.
     *
     * @param horseID ID do cavalo
     * @param maxMove Passada do cavalo
     */
    @Override
    public void makeAMove(int horseID, int maxMove,int count) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        param.add(maxMove);
        param.add(count);
        sendMessage(MsgType.MAKEAMOVE, param);        
    }

    /**
     * Método para terminar a ligação e fechar a classe
     */
    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }
}
