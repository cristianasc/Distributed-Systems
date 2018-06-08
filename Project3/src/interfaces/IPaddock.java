package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author cristianacarvalho
 */
public interface IPaddock extends Remote{
    void proceedToPaddock(int horseID) throws RemoteException;
    void proceedToStartLine() throws RemoteException;
    void goCheckHorses(int spectatorID) throws RemoteException;
    void shutdown() throws RemoteException;
}
