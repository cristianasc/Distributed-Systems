package GeneralRepository;

import Broker.BrokerStates;
import Horse.HorseStates;
import Spectator.SpectatorStates;
import java.util.HashMap;

/**
 *
 * @author cristianacarvalho
 */
public interface IGeneralRepository {
    int getHorseSkills(int horseID);
    void setHorseSkills(int horseID,int value);
    int getnSpectator();
    void setnSpectator(int nSpectators);
    int getnHorses();
    void setnHorses(int nHorses);
    int getnRaces();
    void setnRaces(int nRaces);
    int getDistance();
    void setDistance(int distancia);
    int getHorseWinnerID();
    void setHorseWinnerID(int horseID);
    int getnWinners();
    void setnWinners(int size);
    int getCurrentRace();
    void setActualRace(int race);
    Bet getBetsPerSpectator(int ID);
    void setBetsPerSpectator(int ID, Bet bet);
    void sethorsePositions(int id, int position);
    int gethorsePosition(int id);
    void setArrayPosition(HashMap<Integer,Integer> pos);
    void setCount(int horse, int count);    

    public void setHorseState(int horseID, HorseStates state, int move);

    public void setSpectatorState(int spectatorID, SpectatorStates state);

    public void setSpectatorMoney(int spectatorID, int money);

    public void setSpectatorBet(int spectatorID, int bet);

    public void setBrokerState(BrokerStates state);
}
