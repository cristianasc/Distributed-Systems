package Clients;

import Broker.BrokerStates;
import GeneralRepository.*;
import Horse.HorseStates;
import Spectator.SpectatorStates;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Miguel Maia
 */
public class ClientRepository extends ClientSend implements IGeneralRepository {

    public ClientRepository(InetAddress address, int port) {
        super(address, port);
        System.out.printf("\nCLIENT REPOSITORY\n");
    }

    
    @Override
    public int getHorseSkills(int ID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        Msg msgReceived = (Msg) sendMessage(MsgType.GETHORSEAGILITY, param);
        return (int) msgReceived.getParam().get(0);
    }

    
    @Override
    public void setHorseSkills(int ID, int move) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        param.add(move);
        sendMessage(MsgType.SETHORSEAGILITY, param);
    }

    @Override
    public int getnSpectator() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETNSPECTATORS, null);
        return (int) msgReceived.getParam().get(0);
    }

    
    @Override
    public void setnSpectator(int nSpectators) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(nSpectators);
        sendMessage(MsgType.SETNSPECTATORS, param);
    }

    @Override
    public int getnHorses() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETNHORSES, null);
        return (int) msgReceived.getParam().get(0);
    }

    
    @Override
    public void setnHorses(int nHorses) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(nHorses);
        sendMessage(MsgType.SETNHORSES, param);
    }

    @Override
    public int getnRaces() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETNRACES, null);
        return (int) msgReceived.getParam().get(0);
    }

    @Override
    public void setnRaces(int nRaces) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(nRaces);
        sendMessage(MsgType.SETNRACES, param);
    }

    @Override
    public int getDistance() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETDISTANCIA, null);
        return (int) msgReceived.getParam().get(0);
    }

    
    @Override
    public void setDistance(int distancia) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(distancia);
        sendMessage(MsgType.SETDISTANCIA, param);
    }

    
    @Override
    public int getHorseWinnerID() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETHORSEWINNERID, null);
        return (int) msgReceived.getParam().get(0);
    }

    @Override
    public void setHorseWinnerID(int horseID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(horseID);
        sendMessage(MsgType.SETHORSEWINNERID, param);
    }

    @Override
    public int getnWinners() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETNWINNERS, null);
        return (int) msgReceived.getParam().get(0);
    }

    
    @Override
    public void setnWinners(int size) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(size);
        sendMessage(MsgType.SETNWINNERS, param);
    }

    
    @Override
    public int getCurrentRace() {
        Msg msgReceived = (Msg) sendMessage(MsgType.GETCURRENTRACE, null);
        return (int) msgReceived.getParam().get(0);
    }

    
    @Override
    public void setActualRace(int race) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(race);
        sendMessage(MsgType.SETCURRENTRACE, param);
    }
    
    @Override
    public void setBetsPerSpectator(int ID, Bet bet) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        param.add(bet);
        sendMessage(MsgType.SETBETSPERSPECTATOR, param);
    }

    @Override
    public Bet getBetsPerSpectator(int ID) {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add(ID);
        Msg msgReceived = (Msg) sendMessage(MsgType.GETBETBYSPECTATORID, param);
        return (Bet) msgReceived.getParam().get(0);
    }

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
    
    public void close() {
        sendMessage(MsgType.CLOSE, null);
    }
}
