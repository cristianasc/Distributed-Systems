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
public class HorseStart {
    
    private static String rmiServerHostname;
    private static int rmiServerPort;
    private static String nameEntryBase = "RegisterHandler";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
        int nHorses = 4;
        
        System.out.print("Starting Horses");
        rmiServerHostname = args[0];
        rmiServerPort = Integer.parseInt(args[1]);
        
        Registry registry = getRegistry(rmiServerHostname, rmiServerPort);
        Register register = getRegister(registry);
        IGeneralRepository gr = getGenerealRepository(registry);
        IPaddock pd = getPaddock(registry);
        IStable st = getStable(registry);
        IRacingTrack rt = getRacingTrack(registry);
        IControlCentre cc = getControlCentre(registry);
        
        Horse horse;
        ArrayList<Horse> horses = new ArrayList<>();
        
        for (int i=1; i<= nHorses; i++){
            horse = new Horse(rt, pd,  st,  cc, i, (int) (2+ Math.random() * 5), gr);
            horses.add(horse);
            horse.start();
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
           gr = (IGeneralRepository) registry.lookup("GeneralRepositoryStart");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return gr;
    }
    
    private static IPaddock getPaddock(Registry registry){
       IPaddock pd = null;

       try{ 
           pd = (IPaddock) registry.lookup("PaddockStart");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return pd;
    }
    
    private static IStable getStable(Registry registry){
       IStable st = null;

       try{ 
           st = (IStable) registry.lookup("StableStart");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return st;
    }
    
    private static IRacingTrack getRacingTrack(Registry registry){
       IRacingTrack rt = null;

       try{ 
           rt = (IRacingTrack) registry.lookup("RacingTrackStart");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return rt;
    }
    
    private static IControlCentre getControlCentre(Registry registry){
       IControlCentre cc = null;

       try{ 
           cc = (IControlCentre) registry.lookup("ControlCentreStart");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return cc;
    }
    
}
