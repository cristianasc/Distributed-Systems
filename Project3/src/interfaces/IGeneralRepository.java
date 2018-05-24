package interfaces;

import Broker.BrokerStates;
import monitors.GeneralRepository.Bet;
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
    void setHorseState(int horseID, HorseStates state, int move);
    void setSpectatorState(int spectatorID, SpectatorStates state);
    void setSpectatorMoney(int spectatorID, int money);
    void setSpectatorBet(int spectatorID, int bet);
    void setBrokerState(BrokerStates state);
    void terminate();
}
