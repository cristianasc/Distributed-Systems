/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Horse;
import Stable.*;
import ControlCentre.*;
import Paddock.*;
import RacingTrack.*;
import GeneralRepository.*;

/**
 *
 * @author cristianacarvalho
 */
public class Horse extends Thread{
    
    private HorseStates state;
    private IStable_Horses stHorses;
    private IControlCentre_Horses ccHorses;
    private IPaddock_Horses padHorses;
    private IRacingTrack_Horses rtHorses;
    private GeneralRepository gr;
    private int horseID, nRaces, move;
    
    public Horse(IRacingTrack_Horses rtHorses, IPaddock_Horses padHorses, IStable_Horses stHorses, IControlCentre_Horses ccHorses, int horseID, int move, GeneralRepository gr){
        this.gr = gr;
        this.stHorses = stHorses;
        this.ccHorses = ccHorses;
        this.padHorses = padHorses;
        this.rtHorses = rtHorses;
        this.horseID = horseID;
        this.move = move;
        this.nRaces = gr.getnRaces();
    }
    
    @Override
    public void run() {
        state = HorseStates.AT_THE_STABLE;
        stHorses.proceedToStable(horseID);
        
        state = HorseStates.AT_THE_PADDOCK;
        ccHorses.proceedToPaddock(horseID);
        padHorses.proceedToPaddock(horseID);
        
        state = HorseStates.AT_THE_START_LINE;
        padHorses.proceedToStartLine();
        rtHorses.proceedToStartLine(horseID);
        
        state = HorseStates.RUNNING;
        
        do {
            rtHorses.makeAMove(horseID, move);
            
        } while (!rtHorses.hasFinishLineBeenCrossed(horseID));

        
        System.out.print("\nCavalo " + horseID + " sai da corrida!");
        nRaces--;
       
        if (nRaces != 0){
            state = HorseStates.AT_THE_STABLE;
            stHorses.proceedToStable(horseID);
        }
        else
            System.out.print("\nFim das corridas!");
    }
    
}
