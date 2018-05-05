package Clients;

import java.net.InetAddress;
import java.util.ArrayList;
import ControlCentre.*;
import GeneralRepository.*;

/**
 * 
 * @author Miguel Maia
 *
 */
public class ClientControlCentre extends ClientSend implements IControlCentre{

    public ClientControlCentre(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nCONTROLCENTER CLIENT\n");
    }
    
    @Override
    public void reportResults(ArrayList<Bet> betlist) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(betlist);
        sendMessage(MsgType.REPORTRESULTS, param);
    }
    
    @Override
    public void summonHorsesToPaddock() {
        sendMessage(MsgType.SUMMONHORSESTOPADDOCKCC, null);
    }
    
    @Override
    public ArrayList<Bet> getWinners() {
        Msg msg = (Msg) sendMessage(MsgType.GETWINNERS, null); 
        return (ArrayList<Bet>) msg.getParam().get(0);
    }
    
    @Override
    public void proceedToPaddock(int HorseID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(HorseID);
        sendMessage(MsgType.PROCEEDTOPADDOCK, param);
    }
    
    @Override
    public void goWatchTheRace(int spectatorID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(spectatorID);
        sendMessage(MsgType.GOWATCHTHERACE, param);
    }

    @Override
    public void waitForTheNextRace(int spectatorID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(spectatorID);
        sendMessage(MsgType.WAITFORNEXTRACE, param);
    }

    @Override
    public boolean haveIWon(int spectatorID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(spectatorID);
        Msg msgReceive = (Msg) sendMessage(MsgType.HAVEIWON, param);
        return (boolean) msgReceive.getParam().get(0);
    }

    
    @Override
    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }

}
