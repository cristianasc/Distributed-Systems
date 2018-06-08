package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import monitors.GeneralRepository.Bet;

/**
 *
 * @author cristianacarvalho
 */
public interface IBettingCentre extends Remote{
    Bet acceptTheBets() throws RemoteException;
    void honourTheBets() throws RemoteException;
    boolean areThereAnyWinners(ArrayList<Bet> winners) throws RemoteException;
    void entertainTheGuests() throws RemoteException;
    void placeABet(int spectatorID, int value, int horseID) throws RemoteException;
    void goCollectTheGains(int SpectatorID) throws RemoteException;
    void relaxABit(int SpectatorID) throws RemoteException;
    void shutdown() throws RemoteException;
}
