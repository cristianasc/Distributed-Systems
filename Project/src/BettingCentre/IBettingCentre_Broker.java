/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BettingCentre;
import GeneralRepository.*;

/**
 *
 * @author cristianacarvalho
 */
public interface IBettingCentre_Broker {
    Bet acceptTheBets();
    void honourTheBets();
    void areThereAnyWinners();
    void entertainTheGuests();
}
