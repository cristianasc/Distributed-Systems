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
     */
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        int nHorses = 4;
        int nSpectators = 4;
        int nRaces = 5;
        int distance = 10;
        String[] tmp;
        int stablePort = 0, paddockPort = 0, repositoryPort = 0, bcPort = 0, controlPort = 0;
        InetAddress stableIP = null, paddockIP = null, repositoryIP = null, bcIP = null, controlCenterIP = null;
        GeneralRepository gr = null;
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

        
        Horse horse;
        Spectator spectator;
        
        ArrayList<Horse> horses = new ArrayList<>();
        ArrayList<Spectator> spectators = new ArrayList <>();
        
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
                stBroker = new Stable(gr);
                stHorses = new Stable(gr);
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
                pdSpectator = new Paddock(gr);
                pdHorses = new Paddock(gr);
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
                bcBroker = new BettingCentre(gr);
                bcSpectator = new BettingCentre(gr);
                bcServer = new BettingCentreServer(bcBroker, bcSpectator, bcPort);
                pdServer.start();
            } else {
                bcBroker  = new ClientBettingCentre(paddockIP, paddockPort);
                bcSpectator = new ClientBettingCentre(paddockIP, paddockPort);
            }
            
            
            //CONTROLCENTER
            tmp = prop.getProperty("CONTROLCENTER").split(":");
            
            controlCenterIP = InetAddress.getByName(tmp[0]);
            controlPort = Integer.parseInt(tmp[1]);
            if (NetworkInterface.getByInetAddress(controlCenterIP) != null) {
                
                ccB = new ControlCenter(gr);
                ccH = new ControlCenter(gr);
                ccS = new ControlCenter(gr);
                controlServer = new ControlCenterServer(ccH,ccB,ccS, controlPort);
                controlServer.start();
            } else {
                ccB = new ClientControlCentre(controlCenterIP, controlPort);
                ccH = new ClientControlCentre(controlCenterIP, controlPort);
                ccS = new ClientControlCentre(controlCenterIP, controlPort);
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
    
