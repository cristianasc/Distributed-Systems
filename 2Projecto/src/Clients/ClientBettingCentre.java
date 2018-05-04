/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clients;


import BettingCentre.*;
import GeneralRepository.Bet;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public class ClientBettingCentre extends ClientSend implements IBettingCentre_Spectator, IBettingCentre_Broker{

    public ClientBettingCentre(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nBETTINGCENTRE CLIENT\n");
    }
    
    @Override
    public void placeABet(int spectatorID, int value, int horseID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(spectatorID);
        param.add(value);
        param.add(horseID);
        sendMessage(MsgType.PLACEABET, param); 
    }

    @Override
    public void goCollectTheGains(int SpectatorID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(SpectatorID);
        sendMessage(MsgType.GOCOLLECTTHEGAINS, param); 
    }

    @Override
    public void relaxABit(int SpectatorID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(SpectatorID);
        sendMessage(MsgType.RELAXABIT, param); 
    }

    @Override
    public Bet acceptTheBets() {
        Msg msg = (Msg) sendMessage(MsgType.ACCEPTTHEBETS, null); 
        return (Bet) msg.getParam().get(0);
    }

    @Override
    public void honourTheBets() {
        sendMessage(MsgType.HONOURTHEBETS, null);
    }

    @Override
    public boolean areThereAnyWinners(ArrayList<Bet> winners) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(winners);
        Msg msg = (Msg) sendMessage(MsgType.ARETHEREANYWINNERS, param); 
        return (boolean) msg.getParam().get(0);
    }

    @Override
    public void entertainTheGuests() {
        sendMessage(MsgType.ENTERTAINTHEGUESTS, null);
    }

    
}
