/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AfternoonAtTheRaces;
import Broker.*;
import Horse.*;
import Spectator.*;
import GeneralRepository.*;
import RacingTrack.*;
import Stable.*;
import BettingCentre.*;
import ControlCentre.*;
import Paddock.*;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public class AfternoonAtTheRaces {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        int nHorses = 0;
        int nSpectators = 0;
        int nRaces = 0;
        int distance = 0;
        
        GeneralRepository gr = new GeneralRepository(nHorses, nSpectators, nRaces, distance);
        BettingCentre bt = new BettingCentre(gr);
        ControlCentre cc = new ControlCentre(gr);
        Paddock pad = new Paddock(gr);
        Stable st = new Stable(gr);
        RacingTrack rt = new RacingTrack();
        
        Horse horse;
        Spectator spectator;
        
        ArrayList<Horse> horses = new ArrayList<>();
        ArrayList<Spectator> spectators = new ArrayList <>();
        
        for (int i=1; i<= nHorses; i++){
            horse = new Horse();
            horses.add(horse);
            horse.start();
        }
        
        for (int i=1; i<= nSpectators; i++){
            spectator = new Spectator();
            spectators.add(spectator);
            spectator.start();
        }
        
        Broker br = new Broker((IStable_Broker) st, (IStable_Horses) st, (IRacingTrack_Broker) rt, gr);
        br.start();
        br.join();
        
        for (int i = 0; i < horses.size(); i++) {
            try {
                horse = horses.get(i);
                horse.join();
            } 
            catch (InterruptedException ex) {
            }
        }
        
        for (int i = 0; i < spectators.size(); i++) {
            try {
                spectator = spectators.get(i);
                spectator.join();
            } 
            catch (InterruptedException ex) {
            }
        }
        
        System.out.print("\nFim.");
        
        
        
        
        
    }
    
}
