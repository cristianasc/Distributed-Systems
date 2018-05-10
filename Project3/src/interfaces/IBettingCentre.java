package interfaces;
import java.util.ArrayList;
import monitors.GeneralRepository.Bet;

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
