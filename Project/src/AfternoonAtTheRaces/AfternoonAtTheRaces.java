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
        int nHorses = 4;
        int nSpectators = 4;
        int nRaces = 2;
        int distance = 20;
        
        GeneralRepository gr = new GeneralRepository(nHorses, nSpectators, nRaces, distance);
        BettingCentre bc = new BettingCentre(gr);
        ControlCentre cc = new ControlCentre(gr);
        Paddock pad = new Paddock(gr);
        Stable st = new Stable(gr);
        RacingTrack rt = new RacingTrack(gr);
        
        Horse horse;
        Spectator spectator;
        
        ArrayList<Horse> horses = new ArrayList<>();
        ArrayList<Spectator> spectators = new ArrayList <>();
        
        for (int i=1; i<= nSpectators; i++){
            spectator = new Spectator((IBettingCentre_Spectator) bc, (IControlCentre_Spectator) cc, (IPaddock_Spectator) pad, i, gr);
            spectators.add(spectator);
            spectator.start();
        }
        
        // verificar move!!
        
        for (int i=1; i<= nHorses; i++){
            horse = new Horse((IRacingTrack_Horses) rt, (IPaddock_Horses) pad, (IStable_Horses) st, (IControlCentre_Horses) cc, i, (int) (10 + Math.random() * 5), gr);
            horses.add(horse);
            horse.start();
        }
        
        
        Broker br = new Broker((IBettingCentre_Broker) bc, (IStable_Broker) st, (IStable_Horses) st, (IRacingTrack_Broker) rt,
                (IControlCentre_Broker) cc, gr);
        br.start();
        br.join();
        System.out.print("AQUI!");
        
        
        for (int i = 0; i < spectators.size(); i++) {
            try {
                spectator = spectators.get(i);
                spectator.join();
                System.out.print("AQUI!");
            } 
            catch (InterruptedException ex) {
            }
        }
        
        System.exit(0);
        
    }
}
