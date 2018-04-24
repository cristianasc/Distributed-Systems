/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralRepository;

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
    
    
    
    
}
