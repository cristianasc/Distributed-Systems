/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlCentre;

import GeneralRepository.Bet;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public interface IControlCentre_Broker {
    void reportResults(ArrayList<Bet> betlist);
    void summonHorsesToPaddock();
    void startTheRace();
}
