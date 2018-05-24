package monitors.Stable;

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
public class Stable implements IStable {
    
    private int horseId;
    private boolean callHorses;
    private final IGeneralRepository gr;
    private static int SERVER_PORT;
    private static String rmiServerHostname;
    private static int rmiServerPort;
    private static String nameEntryBase = "RegisterHandler";
    private static String nameEntryObject = "Stable";
    
    /**
     * Construtor da classe
     * @param gr: General Repository
     */
    public Stable(IGeneralRepository gr){
        horseId = 0;
        callHorses = false;
        this.gr = gr;
    }

    /**
     * Método para chamar os cavalos para o estábulo.
     * Os cavalos são bloqueados enquanto o Broker não tiver chamados todos,
     * após ter chamado todos estes são acordados.
     *
     * @param horseID: ID do cavalo
     */
    @Override
    public synchronized void proceedToStable(int horseID) {
        System.out.print("\nO cavalo "+ horseID + " está no estábulo.");
        
        while (!callHorses) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        //Cavalos em espera foram acordados
        horseId++;
        notifyAll();
        
    }
    
    /**
     * Método para chamar os cavalos para o paddock. Os cavalos são acordados 
     * pelo broker. Enquanto não chamar os cavalos todos o broker fica 
     * em espera.
     */
    @Override
    public synchronized void summonHorsesToPaddock() {
        System.out.print("\nBroker vai chamar os Cavalos para paddock.");
        callHorses = true;
        
        // todos os cavalos em espera (no estábulo) serão acordados
        notifyAll();
        
        
        while (horseId != gr.getnHorses()) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
            
        //horseId e callHorses voltam aos valores inicias
        horseId = 0;
        callHorses = false;
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
            register = (Register) registry.lookup(nameEntryObject);
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
            System.out.println("Stable registration exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("Stable not bound exception: " + e.getMessage());
        }

        try {
            UnicastRemoteObject.unexportObject((Remote) this, true);
        } catch (NoSuchObjectException ex) {
        }
        System.out.printf("Stable closed.\n");
    }

}
