/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import monitors.GeneralRepository.Bet;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public interface IControlCentre {
    void reportResults(ArrayList<Bet> betlist);
    void summonHorsesToPaddock();
    ArrayList<Bet> getWinners();
    void close();
    void proceedToPaddock(int HorseID);
    void goWatchTheRace(int spectatorID);
    void waitForTheNextRace(int spectatorID);
    boolean haveIWon(int spectatorID);
    void shutdown();
}
