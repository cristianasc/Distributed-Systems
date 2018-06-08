package interfaces;

import states.*;
import monitors.GeneralRepository.Bet;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 *
 * @author cristianacarvalho
 */
public interface IGeneralRepository extends Remote{
    int getHorseSkills(int horseID) throws RemoteException;
    void setHorseSkills(int horseID,int value) throws RemoteException;
    int getnSpectator() throws RemoteException; 
    void setnSpectator(int nSpectators) throws RemoteException;
    int getnHorses() throws RemoteException;
    void setnHorses(int nHorses) throws RemoteException;
    int getnRaces()throws RemoteException;
    void setnRaces(int nRaces)throws RemoteException;
    int getDistance() throws RemoteException;
    void setDistance(int distancia) throws RemoteException;
    int getHorseWinnerID() throws RemoteException;
    void setHorseWinnerID(int horseID) throws RemoteException;
    int getnWinners() throws RemoteException;
    void setnWinners(int size) throws RemoteException;
    int getCurrentRace() throws RemoteException;
    void setActualRace(int race) throws RemoteException;
    Bet getBetsPerSpectator(int ID) throws RemoteException;
    void setBetsPerSpectator(int ID, Bet bet) throws RemoteException;
    void sethorsePositions(int id, int position) throws RemoteException;
    int gethorsePosition(int id) throws RemoteException;
    void setArrayPosition(HashMap<Integer,Integer> pos) throws RemoteException;
    void setCount(int horse, int count) throws RemoteException;    
    void setHorseState(int horseID, HorseStates state, int move) throws RemoteException;
    void setSpectatorState(int spectatorID, SpectatorStates state) throws RemoteException;
    void setSpectatorMoney(int spectatorID, int money) throws RemoteException;
    void setSpectatorBet(int spectatorID, int bet) throws RemoteException;
    void setBrokerState(BrokerStates state) throws RemoteException;
    void terminate() throws RemoteException;
}
