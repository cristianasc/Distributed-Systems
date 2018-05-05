package AfternoonAtTheRaces;

import Clients.ClientRepository;
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
     * @throws java.lang.InterruptedException InterruptedException
     * @throws java.io.FileNotFoundException FileNotFoundException
     */
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        int nHorses = 4;
        int nSpectators = 4;
        int nRaces = 5;
        int distance = 10;
        String[] tmp;
        int stablePort = 0, paddockPort = 0, repositoryPort = 0, bcPort = 0, controlPort = 0, racingTrackPort = 0;
        InetAddress stableIP = null, paddockIP = null, repositoryIP = null, bcIP = null, controlCenterIP = null,racingtrackIP = null, horseIP=null, spectatorIP= null, brokerIP=null;
        
        IStable st = null;
        IPaddock pd = null;
        IBettingCentre bc = null;
        
        PaddockServer pdServer = null;
        StableServer stableServer = null;
        BettingCentreServer bcServer = null;
        
        IGeneralRepository iGR = null;
        GeneralRepositoryServer repoServer = null;
        
        IControlCentre cc = null;
        ControlCenterServer controlServer = null;

        RacingTrackServer raceServer = null;
        IRacingTrack rt = null;
        
        Horse horse = null;
        Spectator spectator = null;
        Broker br = null;
        
        ArrayList<Horse> horses = new ArrayList<>();
        ArrayList<Spectator> spectators = new ArrayList <>();
        
        boolean horseBol = false;
        boolean brokerBol = false;
        boolean spectatorBol = false;
        
        Properties prop = new Properties();
        
        try {
            prop.load(new FileInputStream("config.properties"));
                        
            //REPOSITORY
            tmp = prop.getProperty("GENERALREPOSITORY").split(":");
            repositoryIP = InetAddress.getByName(tmp[0]);
            repositoryPort = Integer.parseInt(tmp[1]);
            
            if (NetworkInterface.getByInetAddress(repositoryIP) != null) {
                System.err.println("GENERAL REPOSITORY");
                iGR = new GeneralRepository(nSpectators, nHorses, nRaces, distance);
                repoServer = new GeneralRepositoryServer(iGR, repositoryPort);
                repoServer.start();
            } else {
                iGR = new ClientRepository(repositoryIP, repositoryPort);
            }
            
            //STABLE
            tmp = prop.getProperty("STABLE").split(":");
            
            stableIP = InetAddress.getByName(tmp[0]);
            stablePort = Integer.parseInt(tmp[1]);
            
            if (NetworkInterface.getByInetAddress(stableIP) != null) {
                st = new Stable(iGR);
                stableServer = new StableServer(st, stablePort);
                stableServer.start();
            } else {
                st = new ClientStable(stableIP, stablePort);
            }
            
            //PADDOCK
            tmp = prop.getProperty("PADDOCK").split(":");
            
            paddockIP = InetAddress.getByName(tmp[0]);
            paddockPort = Integer.parseInt(tmp[1]);
            
            if (NetworkInterface.getByInetAddress(paddockIP) != null) {
                pd = new Paddock(iGR);
                pdServer = new PaddockServer(pd, paddockPort);
                pdServer.start();
            } else {
                pd = new ClientPaddock(paddockIP, paddockPort);
            }
           
            //BETTING CENTRE
            tmp = prop.getProperty("BETTINGCENTRE").split(":");
            
            bcIP = InetAddress.getByName(tmp[0]);
            bcPort = Integer.parseInt(tmp[1]);
            
            if (NetworkInterface.getByInetAddress(bcIP) != null) {
                bc = new BettingCentre(iGR);
                bcServer = new BettingCentreServer(bc, bcPort);
                bcServer.start();
            } else {
                bc = new ClientBettingCentre(bcIP, bcPort);
            }
            
            
            //CONTROLCENTER
            tmp = prop.getProperty("CONTROLCENTER").split(":");
            
            controlCenterIP = InetAddress.getByName(tmp[0]);
            controlPort = Integer.parseInt(tmp[1]);
            if (NetworkInterface.getByInetAddress(controlCenterIP) != null) {
                
                cc = new ControlCenter(iGR);
                controlServer = new ControlCenterServer(cc, controlPort);
                controlServer.start();
            } else {
                cc = new ClientControlCentre(controlCenterIP, controlPort);
            }
            
            //RACINGTRACK
            tmp = prop.getProperty("RACETRACK").split(":");
            
            racingtrackIP = InetAddress.getByName(tmp[0]);
            racingTrackPort = Integer.parseInt(tmp[1]);
            
            if (NetworkInterface.getByInetAddress(racingtrackIP) != null) {  
                rt = new RacingTrack(iGR);
                raceServer = new RacingTrackServer(rt, racingTrackPort);
                raceServer.start();
            } else {
                rt = new ClientRacingTrack(racingtrackIP, racingTrackPort);
            }
            
            
            
            //CAVALO
            horseIP = InetAddress.getByName(prop.getProperty("HORSE"));
            if (NetworkInterface.getByInetAddress(horseIP) != null) {
                
                for (int i = 1; i <= nHorses; i++) {
                    horse = new Horse(rt, pd, st, cc, i, (int) (2+ Math.random() * 5), iGR);                    
                    horses.add(horse);
                    horse.start();
                    horseBol = true;
                }
            }

            //ESPECTADOR
            spectatorIP = InetAddress.getByName(prop.getProperty("SPECTATOR"));
            if (NetworkInterface.getByInetAddress(spectatorIP) != null) {
                
                for (int i = 1; i <= nSpectators; i++) {
                    spectator = new Spectator(bc, cc, pd, i, iGR);
                    spectators.add(spectator);
                    spectator.start();
                    spectatorBol = true;
                }
            }
            
            //BROKER
            brokerIP = InetAddress.getByName(prop.getProperty("BROKER"));
            if (NetworkInterface.getByInetAddress(brokerIP) != null) {  
                br = new Broker(bc, st ,rt, cc, iGR);                
                br.start();
                
                brokerBol = true;
            }
            
        } catch (Exception ex) {
            
        }
        
        if (spectatorBol) {
            for (int i = 0; i < spectators.size(); i++) {
                try {
                    spectator = spectators.get(i);
                    spectator.join();
                } catch (InterruptedException ex) {
                }
            }
        }
        
        
        if (brokerBol) {
            try {
                br.join();
            } catch (InterruptedException ex) {
            }
            System.exit(0);
        }
        
        
    }  
}
    
