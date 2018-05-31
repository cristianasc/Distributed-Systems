/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import monitors.Stable.*;
import monitors.ControlCentre.*;
import monitors.Paddock.*;
import monitors.RacingTrack.*;
import monitors.GeneralRepository.*;
import states.*;
import interfaces.*;

/**
 *
 * @author cristianacarvalho
 */
public class Horse extends Thread{
    
    private HorseStates state;
    private final IStable st;
    private final IControlCentre cc;
    private final IPaddock pad;
    private final IRacingTrack rt;
    private final IGeneralRepository gr;
    private int horseID, nRaces, move;
    
    public Horse(IRacingTrack rt, IPaddock pad, IStable st, IControlCentre cc, int horseID, int move, IGeneralRepository gr){
        this.gr = gr;
        this.st = st;
        this.cc = cc;
        this.pad = pad;
        this.rt = rt;
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
      st.proceedToStable(horseID);
      proceedToPaddock();
    }

    public void proceedToPaddock(){
      state = HorseStates.AT_THE_PADDOCK;
      gr.setHorseState(horseID,state,move);
      cc.proceedToPaddock(horseID);
      pad.proceedToPaddock(horseID);
      proceedToStartLine();
    }

    public void proceedToStartLine(){
      state = HorseStates.AT_THE_START_LINE;
      gr.setHorseState(horseID,state,move);
      pad.proceedToStartLine();
      rt.proceedToStartLine(horseID);
      makeAMove();
    }

    public void makeAMove(){
        state = HorseStates.RUNNING;
        gr.setHorseState(horseID,state,move);
        int count = 0;

        do {
            count++;
            rt.makeAMove(horseID, move,count);
        } while (!rt.hasFinishLineBeenCrossed(horseID));


        System.out.print("\nCavalo " + horseID + " sai da corrida!");

        state = HorseStates.AT_THE_STABLE;
        gr.setHorseState(horseID,state,move);
        proceedToStable();
    }
       
}
