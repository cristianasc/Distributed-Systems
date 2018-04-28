package BettingCentre;
import GeneralRepository.*;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public interface IBettingCentre_Broker {
    Bet acceptTheBets();
    void honourTheBets();
    boolean areThereAnyWinners(ArrayList<Bet> winners);
    void entertainTheGuests();
}
