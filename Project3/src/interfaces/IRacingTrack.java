package interfaces;

/**
 *
 * @author cristianacarvalho
 */
public interface IRacingTrack {
    void startTheRace();
    void makeAMove(int horseID, int move,int count);
    boolean hasFinishLineBeenCrossed(int horseID);
    void proceedToStartLine(int horseID);
}
