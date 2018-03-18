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
    private int horseID;
    
    
    public Horse(IRacingTrack_Horses rtHorses, IPaddock_Horses padHorses, IStable_Horses stHorses, IControlCentre_Horses ccHorses, int horseID, GeneralRepository gr){
        this.gr = gr;
        this.stHorses = stHorses;
        this.ccHorses = ccHorses;
        this.padHorses = padHorses;
        this.rtHorses = rtHorses;
        this.horseID = horseID;
    }
    
    @Override
    public void run() {
        state = HorseStates.AT_THE_STABLE;
        proceedToStable();
    }
    
    public void proceedToStable(){
        stHorses.proceedToStable(horseID);
        proceedToPaddock();
        
    }
    
    public void proceedToPaddock(){
        state = HorseStates.AT_THE_PADDOCK;
        ccHorses.proceedToPaddock(horseID);
        padHorses.proceedToPaddock(horseID);
        
        proceedToStartLine();
   
    }
    
    public void proceedToStartLine(){
        state = HorseStates.AT_THE_START_LINE;
        padHorses.proceedToStartLine();
        rtHorses.proceedToStartLine();
        
        makeAmove();
        
    }
    
    public void makeAmove(){
        state = HorseStates.RUNNING;
    }
    
}
