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
import Clients.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author cristianacarvalho
 */
public class AfternoonAtTheRaces {

     /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        int nHorses = 4;
        int nSpectators = 4;
        int nRaces = 5;
        int distance = 10;
        String[] tmp;
        int stablePort = 0, paddockPort = 0;
        InetAddress stableIP = null, paddockIP = null;
        IGeneralRepository gr = null;
        IStable_Broker stBroker = null;
        IStable_Horses stHorses = null;
        IPaddock_Horses pdHorses = null;
        IPaddock_Spectator pdSpectator = null;
        PaddockServer pdServer = null;
        StableServer stableServer = null;
        
        Horse horse;
        Spectator spectator;
        
        ArrayList<Horse> horses = new ArrayList<>();
        ArrayList<Spectator> spectators = new ArrayList <>();
        
        Properties prop = new Properties();
        
        try {
            prop.load(new FileInputStream("config.properties"));
            
            
            gr = new GeneralRepository(nHorses, nSpectators, nRaces, distance);
            
            //STABLE
            tmp = prop.getProperty("STABLE").split(":");
            
            stableIP = InetAddress.getByName(tmp[0]);
            stablePort = Integer.parseInt(tmp[1]);
            
            if (NetworkInterface.getByInetAddress(stableIP) != null) {
                stBroker = new Stable(gr);
                stableServer = new StableServer(stBroker, stHorses, stablePort);
                stableServer.start();
            } else {
                stBroker = new ClientStable(stableIP, stablePort);
            }
            
            
            
            
            
            
            //fim dos apostadores
            for (int i = 0; i < spectators.size(); i++) {
                try {
                    spectator = spectators.get(i);
                    spectator.join();
                } 
                catch (InterruptedException ex) {
                }
            }
            
            //fim do broker
            //br.join();

            System.exit(0);
            
        
        } catch (IOException ex) {
            
       
        }
    }  
}
    
