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
        int stablePort = 0, paddockPort = 0, repositoryPort = 0;
        InetAddress stableIP = null, paddockIP = null, repositoryIP = null;
        GeneralRepository gr = null;
        IStable_Broker stBroker = null;
        IStable_Horses stHorses = null;
        IPaddock_Horses pdHorses = null;
        IPaddock_Spectator pdSpectator = null;
        PaddockServer pdServer = null;
        StableServer stableServer = null;
        
        IGeneralRepository rp = null;
        GeneralRepositoryServer repoServer = null;
        
        Horse horse;
        Spectator spectator;
        
        ArrayList<Horse> horses = new ArrayList<>();
        ArrayList<Spectator> spectators = new ArrayList <>();
        
        Properties prop = new Properties();
        
        try {
            prop.load(new FileInputStream("config.properties"));
                        
            gr = new GeneralRepository(nHorses, nSpectators, nRaces, distance);
            
            // Case REPOSITORY
            tmp = prop.getProperty("GENERALREPOSITORY").split(":");
            //System.err.print("\nValor lido no campo IP: "+tmp[0]);
            repositoryIP = InetAddress.getByName(tmp[0]);
            repositoryPort = Integer.parseInt(tmp[1]);
            if (NetworkInterface.getByInetAddress(repositoryIP) != null) {
                //isRepository=true;
                //   System.err.print("\nESTA MAQUINA É REPO");
                rp = new GeneralRepository(nSpectators, nHorses, nRaces, distance);
                repoServer = new GeneralRepositoryServer(rp, repositoryPort);
                repoServer.start();
            } else {
                rp = new ClientRepository(repositoryIP, repositoryPort);
            }
            
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
    
