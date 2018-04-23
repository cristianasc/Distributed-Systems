/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;


import Paddock.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public class ClientPaddock extends ClientSend implements IPaddock_Spectator, IPaddock_Horses {

    public ClientPaddock(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nPADDOCK CLIENT\n");
    }

    @Override
    public void goCheckHorses(int spectatorID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(spectatorID);
        sendMessage(MsgType.GOCHECKHORSES, null); 
    }

    @Override
    public void proceedToPaddock(int horseID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        sendMessage(MsgType.PROCEEDTOPADDOCK, null); 
    }

    @Override
    public void proceedToStartLine() {
        sendMessage(MsgType.PROCEEDTOSTARTLINE, null);  
    }
    
    /**
     * Método para terminar a ligação e fechar a classe
     */
    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }
}
