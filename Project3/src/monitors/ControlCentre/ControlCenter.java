package monitors.ControlCentre;

import monitors.GeneralRepository.*;
import java.util.ArrayList;
import monitors.GeneralRepository.Bet;
import interfaces.*;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cristianacarvalho
 */
public class ControlCenter implements IControlCentre{
    
    private final IGeneralRepository gr;
    private boolean lastHorseToPaddock, lastSpectator, reportResults, allReportResults;
    private int nSpectators, nHorses, spec;
    private ArrayList<Bet> winners;
    private static int SERVER_PORT;
    private static String rmiServerHostname;
    private static int rmiServerPort;
    private static String nameEntryBase = "RegisterHandler";
    private static String nameEntryObject = "ControlCentreStart";
    
    /**
     * Construtor da classe
     * @param gr: General Repository
     */
    public ControlCenter(IGeneralRepository gr)  throws RemoteException{
        this.gr = gr;
        lastHorseToPaddock = false;
        lastSpectator = false;
        reportResults = false;
        this.nSpectators = 0;
        this.spec = 0;
        winners = new ArrayList<>();
        this.nHorses = 0;
    }

    /**
     * Método que vai acordar os espectadores para irem para o Paddock
     * quando o último cavalo chegar ao Paddock. Indica também qual o 
     * cavalo que já está a ir para o Paddock.
     * 
     * @param horseID: ID do cavalo.
     */
    @Override
    public synchronized void proceedToPaddock(int horseID) {
        System.out.print("\nO cavalo "+ horseID +" vai para o paddock.");
        
        nHorses++;
        try {
            if(nHorses==gr.getnHorses()){
                lastHorseToPaddock = true;
                System.out.print("\nTodos os cavalos estão no Paddock.");
                notifyAll();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControlCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que acorda os espectadores que estão no Watching Stand.
     * O Broker é bloqueado enquanto não forem reportados todos os resultados.
     * 
     * @param betlist: ArrayList do tipo Bet com todos os apostadores que ganham.
     */
    @Override
    public synchronized void reportResults(ArrayList<Bet> betlist) {
        
        System.out.print("\nA reportar resultados.");
        winners = betlist;
        reportResults = true;
        notifyAll();
        
        while (!allReportResults) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        reportResults = false;
        allReportResults = false;
        spec = 0;
        nHorses = 0; 

    }

    /**
     * Método que bloqueia o broker até que o último espectador chegue 
     * ao paddock.
     */
    @Override
    public synchronized void summonHorsesToPaddock() {
        while (!lastSpectator) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        lastHorseToPaddock = false;
        lastSpectator = false;
        nSpectators = 0;
    }

    /**
     * Método que bloqueia o espectador enquanto não forem reportados os resultados
     * pelo Broker. Após isto, são acordados.
     * @param spectatorID: ID do espectador
     */
    @Override
    public synchronized void goWatchTheRace(int spectatorID) {
         System.out.print("\nApostador " + spectatorID + " vai para a Watching Stand.");
         while (!reportResults) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
         
        spec++;
        try {
            if (spec == gr.getnSpectator()) {  
                allReportResults = true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControlCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyAll();
        
    }

    /**
     *Médoto que bloqueia o espectador enquanto não tiverem chegado todos os 
     *cavalos ao Paddock. Quando chegarem todos os cavalos e apostadores ao Paddock
     *os espectadores são acordados.
     * 
     * @param spectatorID: ID do espectador
     */
    @Override
    public synchronized void waitForTheNextRace(int spectatorID) {
        
        while (!lastHorseToPaddock) {
            try {
                wait();
            } catch (InterruptedException ex) {
                
            }
        }
        
        nSpectators++; 
        System.out.print("\nO espectador "+ spectatorID +" vai para o Paddock.");
        try {
            if(nSpectators == gr.getnSpectator()){
                lastSpectator = true;
                notifyAll();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControlCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para verificar se determinado espectador
     * @param spectator: ID do espectador
     * @return True se o espectador for um vencedor, False caso contrário
     */
    @Override
    public synchronized boolean haveIWon(int spectator) {
        if (winners == null){
            return false;
        }
        for (int i=0; i<winners.size(); i++){
            if (spectator == winners.get(i).getSpectatorID()){
                return true;
            }
        }
        return false;
    }
    
    /**
     *Método para obter a lista de vencedores.
     * @return winners: ArrayList do tipo Bet
     */
    @Override
    public ArrayList<Bet> getWinners(){
        return winners;
    }    

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static Registry getRegistry(String rmiServerHostname, int rmiServerPort) {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(rmiServerHostname, rmiServerPort);
        } catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage ());
            System.exit(1);
        }
        System.out.println("RMI registry was created!");
        return registry;
    }
    
    private static Register getRegister(Registry registry){
        Register register = null;
        try{ 
            register = (Register) registry.lookup(nameEntryBase);
        }catch (RemoteException e){ 
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit(1);
        }catch (NotBoundException e){ 
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit(1);
        }
        return register;
    }
    
    @Override
    public void shutdown(){
        Registry registry = getRegistry(rmiServerHostname, rmiServerPort);
        Register reg = getRegister(registry);
        
        try {
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("ControlCentre registration exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("ControlCentre not bound exception: " + e.getMessage());
        }

        try {
            UnicastRemoteObject.unexportObject((Remote) this, true);
        } catch (NoSuchObjectException ex) {
        }
        System.out.printf("ControlCentre closed.\n");
    }
    
}
