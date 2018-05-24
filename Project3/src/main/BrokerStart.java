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

/**
 *
 * @author cristianacarvalho
 */
public class BrokerStart {
    
    private static String rmiServerHostname;
    private static int rmiServerPort;
    private static String nameEntryBase = "RegisterHandler";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.print("Starting Broker");
        rmiServerHostname = args[0];
        rmiServerPort = Integer.parseInt(args[1]);
        
        Registry registry = getRegistry(rmiServerHostname, rmiServerPort);
        Register register = getRegister(registry);
        IGeneralRepository gr = getGenerealRepository(registry);
        IBettingCentre bc = getBettingCentre(registry);
        IStable st = getStable(registry);
        IRacingTrack rt = getRacingTrack(registry);
        IControlCentre cc = getControlCentre(registry);
        
        Broker br = new Broker(bc, st, rt, cc, gr);
        br.start();
        
        try { 
            br.join();
        } catch (InterruptedException e) {
        }
        
        
        try {
            gr.terminate();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        
        System.out.println("End of Operations");
        
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
    
    private static IStable getStable(Registry registry){
       IStable st = null;

       try{ 
           st = (IStable) registry.lookup("Stable");
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
           rt = (IRacingTrack) registry.lookup("RacingTrack");
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
           cc = (IControlCentre) registry.lookup("ControlCentre");
       }catch (RemoteException e){ 
           System.exit(1);
       }catch (NotBoundException e){
           System.exit(1);
       }

       return cc;
    }
    
}
