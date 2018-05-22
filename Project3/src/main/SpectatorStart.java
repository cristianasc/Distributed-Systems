/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import interfaces.*;
import java.rmi.*;
import static java.rmi.registry.LocateRegistry.getRegistry;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author cristianacarvalho
 */
public class SpectatorStart {
    private static String rmiServerHostname;
    private static int rmiServerPort;
    private static String nameEntryBase = "RegisterHandler";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int nSpectators = 4;
        
        Registry registry = getRegistry(rmiServerHostname, rmiServerPort);
        Register register = getRegister(registry);
        IGeneralRepository gr = getGenerealRepository(registry);
        IBettingCentre bc = getBettingCentre(registry);
        IPaddock pd = getPaddock(registry);
        IControlCentre cc = getControlCentre(registry);
        
        Spectator spectator;
        ArrayList<Spectator> spectators = new ArrayList <>();
        
        for (int i=1; i<= nSpectators; i++){
            spectator = new Spectator(bc, cc, pd, i, gr);
            spectators.add(spectator);
            spectator.start();
        }
        
        
        for (int i = 0; i < spectators.size(); i++) {
            try {
                spectator = spectators.get(i);
                spectator.join();
            } 
            catch (InterruptedException ex) {
            }
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
    
    private static IGeneralRepository getGenerealRepository(Registry registry){
       IGeneralRepository gr = null;

       try{ 
           gr = (IGeneralRepository) registry.lookup("GeneralRepository");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return gr;
    }
    
    private static IBettingCentre getBettingCentre(Registry registry){
       IBettingCentre bc = null;

       try{ 
           bc = (IBettingCentre) registry.lookup("BettingCentre");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return bc;
    }
    
    private static IControlCentre getControlCentre(Registry registry){
       IControlCentre cc = null;

       try{ 
           cc = (IControlCentre) registry.lookup("ControlCentre");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return cc;
    }
    
    private static IPaddock getPaddock(Registry registry){
       IPaddock pd = null;

       try{ 
           pd = (IPaddock) registry.lookup("Paddock");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return pd;
    }
    
}
