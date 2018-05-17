/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitors.GeneralRepository;

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
public class GeneralRepositoryStart {
    
     private static int SERVER_PORT;
    private static String rmiServerHostname;
    private static int rmiServerPort;
    private static String nameEntryBase = "RegisterHandler";
    private static String nameEntryObject = "GeneralRepositoryStart";


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.print("Starting GeneralRepository");
        SERVER_PORT = Integer.parseInt(args[0]);
        rmiServerHostname = args[1];
        rmiServerPort = Integer.parseInt(args[2]);
        
        Registry registry = getRegistry(rmiServerHostname, rmiServerPort);
        Register register = getRegister(registry);
        
        /* create and install the security manager */
        if (System.getSecurityManager () == null){
            System.setSecurityManager (new SecurityManager ());
            System.out.println("Security manager was installed!");
        }
        
        GeneralRepository gr = new GeneralRepository(4,4,4,10);
        IGeneralRepository iGR = null;
        
        try{ 
            iGR = (IGeneralRepository) UnicastRemoteObject.exportObject((Remote) gr, SERVER_PORT);
        }catch (RemoteException e){
            System.out.println("ComputeEngine stub generation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit(1);
        }
        
        try{ 
            register.bind (nameEntryObject, (Remote) iGR);
        }catch (RemoteException e){ 
            System.out.println("ComputeEngine registration exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit(1);
        }catch (AlreadyBoundException e){ 
            System.out.println("ComputeEngine already bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit(1);
        }
        System.out.println("ComputeEngine object was registered!");    
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
    
}
