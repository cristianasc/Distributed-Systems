/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BettingCentre;

/**
 *
 * @author Miguel
 */
public interface IBettingCentre_Spectator {
    void placeABet(int spectatorID, int value, int horseID);
    void goCollectTheGains(int SpectatorID);
    void relaxABit(int SpectatorID);
}
