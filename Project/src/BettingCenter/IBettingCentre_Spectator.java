/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BettingCenter;

/**
 *
 * @author Miguel
 */
public interface IBettingCentre_Spectator {
    void placeABet(int spectatorID, double value, int horseID);
    double goCollectTheGains(int SpectatorID);
}
