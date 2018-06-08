/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.Serializable;

/**
 *
 * @author cristianacarvalho
 */
public interface IBet extends Serializable {
    public int getSpectatorID();
    public void setSpectatorID(int SpectatorID);
    public int getHorseID();
    void setHorseID(int horseID);
    int getBetvalue();
    void setBetvalue(int Betvalue);
}
