/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlCentre;

import GeneralRepository.Bet;
import java.util.ArrayList;

/**
 * Interface para Centro de Controlo. Tem 2 instâncias possiveis, Centro de
 * Controlo Local (ControlCenterLocal) ou cliente (RemoteControlCenter) para
 * servidor remoto (ControlCenterServer).
 *
 * @author Ricardo Martins
 */
public interface IControlCenter {
    void reportResults(ArrayList<GeneralRepository.Bet> betlist);
    void summonHorsesToPaddock();
    ArrayList<GeneralRepository.Bet> getWinners();
    void proceedToPaddock(int HorseID);
    void goWatchTheRace(int spectatorID);
    void waitForTheNextRace(int spectatorID);
    boolean haveIWon(int spectatorID);
    
    public void close();
}
