package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author cristianacarvalho
 */
public interface IStable extends Remote{
    void summonHorsesToPaddock() throws RemoteException;
    void proceedToStable(int horseID) throws RemoteException;
    void shutdown() throws RemoteException;
}
