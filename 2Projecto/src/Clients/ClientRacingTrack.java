/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;

import RacingTrack.*;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * @author Miguel Maia
 */
public class ClientRacingTrack extends ClientSend implements IRacingTrack {

    public ClientRacingTrack(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nRACINGTRACK CLIENT \n");
    }

    @Override
    public void proceedToStartLine(int horseID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        sendMessage(MsgType.PROCEEDTOSTARTLINE, param);
    }

    @Override
    public boolean hasFinishLineBeenCrossed(int ID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        Msg msgReceived = (Msg) sendMessage(MsgType.HASFINISHLINEBEENCROSSED, param);
        return (boolean) msgReceived.getParam().get(0);  //garantir que boolean de resposta vem na posição 0
    }

    @Override
    public void startTheRace() {
        sendMessage(MsgType.STARTTHERACE, null);
    }

    @Override
    public void makeAMove(int horseID, int maxMove,int count) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        param.add(maxMove);
        param.add(count);
        sendMessage(MsgType.MAKEAMOVE, param);        
    }

    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }
}
