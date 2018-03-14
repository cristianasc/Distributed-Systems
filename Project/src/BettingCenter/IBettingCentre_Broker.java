/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BettingCenter;

/**
 *
 * @author cristianacarvalho
 */
public interface IBettingCentre_Broker {
    void acceptTheBets(int spectatorID, double value, int horseID);
    void honourTheBets();
}
