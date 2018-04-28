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
