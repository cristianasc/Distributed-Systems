package monitors.RacingTrack;

import monitors.GeneralRepository.*;
import interfaces.*;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static monitors.RacingTrack.RacingTrackStart.rmiServerHostname;
import static monitors.RacingTrack.RacingTrackStart.rmiServerPort;

/**
 *
 * @author cristianacarvalho
 */
public class RacingTrack implements IRacingTrack{
    
    private boolean makeAMove;
    private boolean lastHorse;
    private final IGeneralRepository gr;
    private HashMap<Integer, Integer> positions;
    private int next, nHorses, position, nHorsesInRace;
    private static int SERVER_PORT;
    private static String nameEntryBase = "RegisterHandler";
    private static String nameEntryObject = "RacingTrack";
    
    /**
     * Construtor da classe
     * @param gr: General Repository
     */
    public RacingTrack(IGeneralRepository gr) throws RemoteException{
        this.gr = gr;
        this.nHorses = gr.getnHorses();
        next = 0;
        makeAMove = false;
        positions = new HashMap<>();
        nHorsesInRace = gr.getnHorses();
    }
    
    /**
     * Método que vai bloquear os cavalos até que a corrida comece.
     * @param horseID: ID do cavalo
     */
    @Override
    public synchronized void proceedToStartLine(int horseID) {
        while (!makeAMove) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }         
    }

    /**
     * Método que bloqueia o cavalo até que seja a sua vez de se mover e calcula
     * o movimento que o cavalo irá efetuar sempre que se quiser mover. Este movimento
     * é Random mas sempre tendo em consideração a agilidade (passada) do cavalo.
     * Quando o cavalo passa a meta é eliminado da corrida e quando todos passarem 
     * a meta então a corrida termina.
     * 
     * @param horse: ID do cavalo
     * @param move: agilidade do cavalo (passada)
     */
    @Override
    public synchronized void makeAMove(int horse, int move,int count){
        //cavalo espera até ser a sua vez de se mover
        while (horse != next) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        
        //calcular movimento
        move = 1 + (int) (Math.random() * ((move + 1)));
        if (positions.containsKey(horse))
            position = positions.get(horse);
        else
            position = 0;
        
        position += move;
        positions.put(horse, position);
       
        try {
            gr.sethorsePositions(horse, position);
            gr.setArrayPosition(positions);
            gr.setCount(horse,count);

            System.out.print("\nCavalo " + horse+ " mexeu-se " + move + ", fica na posição " + position);

            if (position >= gr.getDistance()){
                System.out.print("\nCavalo " + horse+ " passou a meta.");

                if(nHorsesInRace == gr.getnHorses())
                    gr.setHorseWinnerID(horse);

                if (positions.containsKey(horse))
                    positions.remove(horse);

                nHorsesInRace--; 
            }

            gr.setArrayPosition(positions);

            if (nHorsesInRace != 0){
                next = ((next % nHorses) + 1);
                if (!positions.containsKey(next)) {
                    do {
                        next = positions.keySet().iterator().next();
                    } while (!positions.containsKey(next));
                }
            }

            if (nHorsesInRace == 0){
                System.out.print("\nO último cavalo passou a meta.");
                lastHorse = true;
            }

            notifyAll();
        } catch (RemoteException ex) {
            Logger.getLogger(RacingTrack.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    /**
     * Método que indica se um cavalo já terminou ou não a corrida. Se ainda
     * estiver na lista de posições da corrida, então ainda não a terminou.
     * 
     * @param horseID ID do cavalo que se pretende saber se já terminou a corrida.
     * @return True se já não estiver na lista de posições e False se ainda 
     * estiver.
     */
    @Override
    public synchronized boolean hasFinishLineBeenCrossed(int horseID) {
        return !positions.containsKey(horseID);
    }

    /**
     * Método que inicia a corrida de cavalos.
     * Os cavalos começam todos na posição 0 e vão ser acordados pelo Broker.
     * O Broker fica bloqueado e a corrida só acaba quando o útlimo cavalo
     * chegar ao fim da meta.
     */
    @Override
    public synchronized void startTheRace() {
        System.out.print("\nCOMEÇA A CORRIDA");
        
        next = 1;
        makeAMove = true;
        try {
            nHorsesInRace = gr.getnHorses();
            //posições iniciais dos cavalos
            for (int i = 0; i < gr.getnHorses(); i++) {
                positions.put((i + 1), 0);
            }

            //Acordar os cavalos
            notifyAll();
            for (int i = 0; i < gr.getnHorses(); i++){
                System.out.print("\nCavalo " + (i+1) + " na posição " + positions.get(i+1));
            }

             while (!lastHorse) {
                try {
                    wait();
                } catch (InterruptedException ex) {

                }
            }
            lastHorse = false;
            makeAMove = false;
            System.out.print("\nFIM DA CORRIDA.");
        } catch (RemoteException ex) {
            Logger.getLogger(RacingTrack.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println("RacingTrack registration exception: " + e.getMessage());
        } catch (NotBoundException e) {
            System.out.println("RacingTrack not bound exception: " + e.getMessage());
        }

        try {
            UnicastRemoteObject.unexportObject((Remote) this, true);
        } catch (NoSuchObjectException ex) {
        }
        System.out.printf("RacingTrack closed.\n");
    }

}
