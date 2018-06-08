package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author cristianacarvalho
 */
public interface IRacingTrack extends Remote{
    void startTheRace() throws RemoteException;
    void makeAMove(int horseID, int move,int count) throws RemoteException;
    boolean hasFinishLineBeenCrossed(int horseID) throws RemoteException;
    void proceedToStartLine(int horseID) throws RemoteException;
    void shutdown() throws RemoteException;
}
