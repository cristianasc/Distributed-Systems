/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlCentre;

/**
 *
 * @author cristianacarvalho
 */
public interface IControlCentre_Spectator {
    void goWatchTheRace(int spectatorID);
    void waitForTheNextRace(int spectatorID);
    boolean haveIWon(int spectatorID);
}
