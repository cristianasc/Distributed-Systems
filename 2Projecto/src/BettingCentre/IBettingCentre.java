package BettingCentre;
import GeneralRepository.*;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public interface IBettingCentre {
    Bet acceptTheBets();
    void honourTheBets();
    boolean areThereAnyWinners(ArrayList<Bet> winners);
    void entertainTheGuests();
    void placeABet(int spectatorID, int value, int horseID);
    void goCollectTheGains(int SpectatorID);
    void relaxABit(int SpectatorID);
}
