/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import monitors.GeneralRepository.Bet;
import java.util.ArrayList;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author cristianacarvalho
 */
public interface IControlCentre extends Remote{
    void reportResults(ArrayList<Bet> betlist) throws RemoteException;
    void summonHorsesToPaddock() throws RemoteException;
    ArrayList<Bet> getWinners() throws RemoteException;
    void close() throws RemoteException;
    void proceedToPaddock(int HorseID) throws RemoteException;
    void goWatchTheRace(int spectatorID) throws RemoteException;
    void waitForTheNextRace(int spectatorID) throws RemoteException;
    boolean haveIWon(int spectatorID) throws RemoteException;
    void shutdown() throws RemoteException;
}
