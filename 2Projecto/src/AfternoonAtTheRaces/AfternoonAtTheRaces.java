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
        GeneralRepository gr = new GeneralRepository(nHorses, nSpectators, nRaces, distance);
        BettingCentre bc = new BettingCentre(gr);
        ControlCenter cc = new ControlCenter(gr);
        Paddock pad = new Paddock(gr);
        Stable st = new Stable(gr);
        RacingTrack rt = new RacingTrack(gr);
        
        IStable_Broker stBroker = null;
        IStable_Horses stHorses = null;
        IPaddock_Horses pdHorses = null;
        IPaddock_Spectator pdSpectator = null;
        IBettingCentre_Broker bcBroker = null;
        IBettingCentre_Spectator bcSpectator = null;
        PaddockServer pdServer = null;
        StableServer stableServer = null;
        BettingCentreServer bcServer = null;
        
        IGeneralRepository iGR = null;
        GeneralRepositoryServer repoServer = null;
        
        IControlCentre_Broker ccB;
        IControlCentre_Spectator ccS;
        IControlCentre_Horses ccH;
        ControlCenterServer controlServer = null;

        RacingTrackServer raceServer = null;
        IRacingTrack_Broker rtB = null;
        IRacingTrack_Horses rtH = null;
        ClientRacingTrack rtC = null;
        
        Horse horse = null;
        Spectator spectator = null;
        Broker br = null;
        
        ArrayList<Horse> horses = new ArrayList<>();
        ArrayList<Spectator> spectators = new ArrayList <>();
        
        boolean Horse = false;
        boolean broker=false;
        boolean Spectator = false;
        
        Properties prop = new Properties();
        
        try {
            prop.load(new FileInputStream("config.properties"));
                        
            //REPOSITORY
            tmp = prop.getProperty("GENERALREPOSITORY").split(":");
            repositoryIP = InetAddress.getByName(tmp[0]);
            repositoryPort = Integer.parseInt(tmp[1]);
            
            if (NetworkInterface.getByInetAddress(repositoryIP) != null) {
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
                stBroker = new Stable(iGR);
                stHorses = new Stable(iGR);
                stableServer = new StableServer(stBroker, stHorses, stablePort);
                stableServer.start();
            } else {
                stBroker = new ClientStable(stableIP, stablePort);
                stHorses = new ClientStable(stableIP, stablePort);
            }
            
            //PADDOCK
            tmp = prop.getProperty("PADDOCK").split(":");
            
            paddockIP = InetAddress.getByName(tmp[0]);
            paddockPort = Integer.parseInt(tmp[1]);
            
            if (NetworkInterface.getByInetAddress(paddockIP) != null) {
                pdSpectator = new Paddock(iGR);
                pdHorses = new Paddock(iGR);
                pdServer = new PaddockServer(pdSpectator, pdHorses, paddockPort);
                pdServer.start();
            } else {
                pdSpectator = new ClientPaddock(paddockIP, paddockPort);
                pdHorses = new ClientPaddock(paddockIP, paddockPort);
            }
           
            //BETTING CENTRE
            tmp = prop.getProperty("BETTINGCENTRE").split(":");
            
            bcIP = InetAddress.getByName(tmp[0]);
            bcPort = Integer.parseInt(tmp[1]);
            
            if (NetworkInterface.getByInetAddress(bcIP) != null) {
                bcBroker = new BettingCentre(iGR);
                bcSpectator = new BettingCentre(iGR);
                bcServer = new BettingCentreServer(bcBroker, bcSpectator, bcPort);
                bcServer.start();
            } else {
                System.err.println("swsdfsdgfsafdgdfghdsfgsdfgdsfg");
                bcBroker  = new ClientBettingCentre(paddockIP, paddockPort);
                bcSpectator = new ClientBettingCentre(paddockIP, paddockPort);
            }
            
            
            //CONTROLCENTER
            tmp = prop.getProperty("CONTROLCENTER").split(":");
            
            controlCenterIP = InetAddress.getByName(tmp[0]);
            controlPort = Integer.parseInt(tmp[1]);
            if (NetworkInterface.getByInetAddress(controlCenterIP) != null) {
                
                ccB = new ControlCenter(iGR);
                ccH = new ControlCenter(iGR);
                ccS = new ControlCenter(iGR);
                controlServer = new ControlCenterServer(ccH,ccB,ccS, controlPort);
                controlServer.start();
            } else {
                ccB = new ClientControlCentre(controlCenterIP, controlPort);
                ccH = new ClientControlCentre(controlCenterIP, controlPort);
                ccS = new ClientControlCentre(controlCenterIP, controlPort);
            }
            
            //RACINGTRACK
            tmp = prop.getProperty("RACETRACK").split(":");
            
            racingtrackIP = InetAddress.getByName(tmp[0]);
            racingTrackPort = Integer.parseInt(tmp[1]);
            if (NetworkInterface.getByInetAddress(racingtrackIP) != null) {
                
                rtH = new RacingTrack(iGR);
                rtB = new RacingTrack(iGR);
                raceServer = new RacingTrackServer(rtB,rtH, racingTrackPort);
                raceServer.start();
            } else {
                rtH = new ClientRacingTrack(racingtrackIP, racingTrackPort);
                rtB = new ClientRacingTrack(racingtrackIP, racingTrackPort);
            }
            
            
            //CAVALO
            horseIP = InetAddress.getByName(prop.getProperty("HORSE"));
            if (NetworkInterface.getByInetAddress(horseIP) != null) {
                
                for (int i = 1; i <= nHorses; i++) {
                    horse = new Horse((IRacingTrack_Horses) rt, (IPaddock_Horses) pad, (IStable_Horses) st, (IControlCentre_Horses) cc, i, (int) (2+ Math.random() * 5), gr);
                    horses.add(horse);
                    horse.start();
                }
            }

            //ESPECTADOR
            spectatorIP = InetAddress.getByName(prop.getProperty("PUNTER"));
            if (NetworkInterface.getByInetAddress(spectatorIP) != null) {
                
                for (int i = 1; i <= nSpectators; i++) {
                    spectator = new Spectator((IBettingCentre_Spectator) bc, (IControlCentre_Spectator) cc, (IPaddock_Spectator) pad, i, gr);
                    spectators.add(spectator);
                    spectator.start();
                    Spectator = true;
                }
            }
            
            //BROKER
            brokerIP = InetAddress.getByName(prop.getProperty("MANAGER"));
            if (NetworkInterface.getByInetAddress(brokerIP) != null) {
                
                br = new Broker((IBettingCentre_Broker) bc, (IStable_Broker) st, (IStable_Horses) st, (IRacingTrack_Broker) rt,
                (IControlCentre_Broker) cc, gr);
                br.start();
                
                broker = true;
            }
            
            
            
        } catch (Exception ex) {
            
        }
        //fim do broker
                br.join();
        
        //fim dos apostadores
            for (int i = 0; i < spectators.size(); i++) {
                try {
                    spectator = spectators.get(i);
                    spectator.join();
                } 
                catch (InterruptedException ex) {
                }
            }
            
            System.exit(0);
    }  
}
    
