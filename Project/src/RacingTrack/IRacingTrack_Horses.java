/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RacingTrack;

/**
 *
 * @author cristianacarvalho
 */
public interface IRacingTrack_Horses {
    void makeAMove();
    boolean hasFinishLineBeenCrossed(int horseID);
    void proceedToStartLine();
}
