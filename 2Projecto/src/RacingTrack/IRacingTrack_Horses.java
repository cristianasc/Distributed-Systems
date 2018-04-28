package RacingTrack;

/**
 *
 * @author cristianacarvalho
 */
public interface IRacingTrack_Horses {
    void makeAMove(int horseID, int move,int count);
    boolean hasFinishLineBeenCrossed(int horseID);
    void proceedToStartLine(int horseID);
}
