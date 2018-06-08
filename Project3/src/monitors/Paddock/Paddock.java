package monitors.Paddock;

import monitors.GeneralRepository.*;
import interfaces.*;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author cristianacarvalho
 */
public class Paddock implements IPaddock{
    
    private int nHorse, horseNTotal, spectator, spectatorNTotal, spectatorToBet;
    private boolean goCheckHorses, goToStartLine;
    private final IGeneralRepository gr;
    private static int SERVER_PORT;
    private static String rmiServerHostname;
    private static int rmiServerPort;
    private static String nameEntryBase = "RegisterHandler";
    private static String nameEntryObject = "PaddockStart";
    
    
    /**
     * Construtor da classe
     * @param gr: General Repository
     */
    public Paddock(IGeneralRepository gr) throws RemoteException{
        this.gr = gr;
        horseNTotal = gr.getnHorses();
        spectatorNTotal = gr.getnSpectator();
        nHorse = 0;
        spectator = 0;
        spectatorToBet = 0;
        goCheckHorses = false;
    }
    
    /**
     * Método que bloqueia os cavalos até que o último espectador chegue ao 
     * Paddock. Quando isto acontece a flag goCheckHorses fica igual a True e o
     * cavalo deixa de estar bloqueado.
     * 
     * @param horseID: ID do Cavalo
     */
    @Override
    public synchronized void proceedToPaddock(int horseID) {
        while (!goCheckHorses) {
            try {
                wait();      
            } catch (InterruptedException ex) {
                
            }
        }
        
        System.out.print("\nCavalo " + horseID + " a ir para a StartLine.");
        nHorse++;
    }

    /**
     * Método que acorda os espectadores e os cavalos 
     * quando estes estiverem todos no Paddock.
     * O espectador fica bloqueado até os cavalos não sairem todos do paddock.
     * Após isto, os espectadores podem apostar.
     * 
     * @param spectatorID variável que especifica qual o espectador.
     */
    @Override
    public synchronized void goCheckHorses(int spectatorID) {
        spectator++;
        if(spectator == spectatorNTotal){
            goCheckHorses = true;
             
            notifyAll();
            System.out.print("\nOs espetadores estão no paddock.");
        }
        
        
        while (!goToStartLine) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        System.out.print("\nO espectador "+ spectatorID + " vai apostar.");
        spectatorToBet++;
        if (spectatorToBet == spectatorNTotal){
            spectatorToBet = 0;
            spectator = 0;
            goToStartLine = false;
        } 
    }

    /**
     * Método que acorda os espectadores quando o último cavalo sair do Paddock.
     */
    @Override
    public synchronized void proceedToStartLine() {
        if(nHorse == horseNTotal){
            System.out.print("\nOs cavalos sairam todos do paddock.");
            goToStartLine = true;
            nHorse = 0;
            notifyAll();
            goCheckHorses = false;
        }
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
            System.out.println("Paddock registration exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("Paddock not bound exception: " + e.getMessage());
        }

        try {
            UnicastRemoteObject.unexportObject((Remote) this, true);
        } catch (NoSuchObjectException ex) {
        }
        System.out.printf("Paddock closed.\n");
    }
}
