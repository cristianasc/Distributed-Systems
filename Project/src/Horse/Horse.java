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
    private final IStable_Horses stHorses;
    private final IControlCentre_Horses ccHorses;
    private final IPaddock_Horses padHorses;
    private final IRacingTrack_Horses rtHorses;
    private final GeneralRepository gr;
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
        gr.setHorseState(horseID,state,move);
        proceedToStable();
    }
     
    public void proceedToStable(){
      stHorses.proceedToStable(horseID);
      proceedToPaddock();
    }

    public void proceedToPaddock(){
      state = HorseStates.AT_THE_PADDOCK;
      gr.setHorseState(horseID,state,move);
      ccHorses.proceedToPaddock(horseID);
      padHorses.proceedToPaddock(horseID);
      proceedToStartLine();
    }

    public void proceedToStartLine(){
      state = HorseStates.AT_THE_START_LINE;
      gr.setHorseState(horseID,state,move);
      padHorses.proceedToStartLine();
      rtHorses.proceedToStartLine(horseID);
      makeAMove();
    }

    public void makeAMove(){
      state = HorseStates.RUNNING;
      gr.setHorseState(horseID,state,move);

      do {
        rtHorses.makeAMove(horseID, move);
      } while (!rtHorses.hasFinishLineBeenCrossed(horseID));


      System.out.print("\nCavalo " + horseID + " sai da corrida!");

      state = HorseStates.AT_THE_STABLE;
      gr.setHorseState(horseID,state,move);
      proceedToStable();
    }
       
}
