package monitors.GeneralRepository;

import Broker.BrokerStates;
import Horse.HorseStates;
import Spectator.SpectatorStates;
import interfaces.IGeneralRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 *
 * @author cristianacarvalho
 */
public class GeneralRepository implements IGeneralRepository{
    private int nHorses, nSpectators, nRaces, nWinners, distance, horseWinner;
    private HashMap<Integer, Integer> horsePositions;
    private HashMap<Integer, Bet> betsPerSpectator;
    private int currentRace;
    
    private static PrintWriter pw;
    private final File log;
    private BrokerStates BrokerState;
    private SpectatorStates[] statesSpectator;
    private int[] spectatorMoney;
    private int[] spectatorBet;
    private HorseStates[] statesHorses;
    private int[] HorseAgility;
    private int[] count;
    private HashMap<Integer,Integer> pos;
    
    /**
     * Construtor da classe
     *
     * @param nHorses - Numero de cavalos
     * @param nSpectators - Numero de espectadores
     * @param nRaces - Número de corridas
     * @param distance - Distância de cada corrida
     */
    
    public GeneralRepository(int nHorses, int nSpectators, int nRaces, int distance){
        this.nHorses = nHorses;
        this.nSpectators = nSpectators;
        this.nRaces = nRaces;
        this.distance = distance;
        this.horsePositions = new HashMap<>();
        this.betsPerSpectator = new HashMap<>();
        this.currentRace = 0;
        this.pos = new HashMap();
        
        this.BrokerState = BrokerStates.OPENING_THE_EVENT;
        this.statesSpectator = new SpectatorStates[nSpectators];
        this.statesHorses = new HorseStates[nHorses];
        this.spectatorMoney = new int[nSpectators];
        this.HorseAgility = new int[nHorses];
        this.spectatorMoney = new int[nSpectators];
        this.spectatorBet = new int[nSpectators];
        this.count = new int[nHorses];        
        
        for (int i = 0; i < nHorses; i++) {
            statesHorses[i] = HorseStates.AT_THE_STABLE;
        }
        
        for (int i = 0; i < nSpectators; i++) {
            statesSpectator[i] = SpectatorStates.WAITING_FOR_A_RACE_TO_START;
        }
        
        for (int i = 0; i < spectatorMoney.length; i++) {
            spectatorMoney[i] = 0;            
        }
        
        for (int i = 0; i < nHorses; i++) {
            HorseAgility[i] = 0;            
        }
        
        for (int i = 0; i < nHorses; i++) {
            count[i] = 0;
        }
        
        for (int i = 0; i < nSpectators; i++) {
            spectatorBet[i] = 0;
        }
        
        

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
        String filename = "assignment1T1G7_" + date.format(today) + ".txt";

        this.log = new File(filename);
        writeInit();          
    }
    
    public synchronized void FirstLine(){
        pw.printf("\t%4s\t", BrokerState.toString());
        
        for (int i=0; i < nSpectators; i++){
            if (statesSpectator[i] != null) {
                pw.printf("%3s %3d   ", statesSpectator[i].toString(), spectatorMoney[i]);
            }            
        }
        
        pw.printf("%2d   ", currentRace);  
        for (int i=0; i < nHorses; i++){
            if (statesHorses[i] != null) {
                pw.printf("%3s  %4d ",  statesHorses[i].toString(), HorseAgility[i]);
            }            
        }
        
        //Second part of logger
        pw.println();
        pw.flush();
        
        pw.printf("\t%d   %d   ", currentRace,distance);
        
        for (int i = 0; i < nSpectators; i++) {
            pw.printf(" %d   %d  ", spectatorBet[i], spectatorMoney[i]);
        }        
        
        int totalAgility = 0;
        for (int i = 0; i < nHorses; i++) {
            totalAgility += HorseAgility[i];
        }
        int aux;
        double agility;
        for (int i = 0; i < nHorses; i++) {
            if (HorseAgility[i] == 0 || totalAgility == 0){
                agility = 0.0;
            }else{                
                agility =  ((double) HorseAgility[i]/(double) totalAgility) * 100;
            }
            if (horsePositions.get(i) != null) {
                if (!pos.containsKey(i)){
                    pw.printf(" %d %d  %d  %d  ",(int) agility,count[i],horsePositions.get(i),i);
                }else{
                    aux = 0;
                    pw.printf(" %d %d  %d  %d  ",(int) agility,count[i],horsePositions.get(i),aux);
                }
            }
            else{
                aux = 0;
                if (!pos.containsKey(i)){
                    pw.printf(" %d %d  %d  %d  ",(int) agility,count[i],aux,i);
                }else{
                    aux = 0;
                    pw.printf(" %d %d  %d  %d  ",(int) agility,count[i],aux,aux);
                }
            }
        }
        
        pw.println();
        pw.flush();
    }
    

 
     private void writeInit(){
        try{
            pw = new PrintWriter(log);
            
            pw.println("                               AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem ");
            pw.println();
            pw.println("\tMAN/BRK\t\t  SPECTATOR/BETTER\t\t     HORSE/JOCKEY PAIR at Race RN");
            
            pw.print("\tStat\t");
            for (int i=0; i < nSpectators; i++){
                pw.printf("St%d  Am%d  ",i,i);
            }
            
            pw.print(" RN   ");
            for (int i=0; i < nHorses; i++){
                pw.printf("Hj%d  Len%d  ",i,i);
            }
            pw.println();
            pw.print("\t\t                                Race RN Status                            ");
            pw.println();
            pw.print("\tRn  Dist ");
            for (int i=0; i < nSpectators; i++){
                pw.printf(" BS%d BA%d",i,i);
            }
            for (int i=0; i < nHorses; i++){
                pw.printf(" Od%d N%d Ps%d SD%d",i,i,i,i);
            }
                
            pw.println();            
            pw.flush();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public synchronized void setActualRace(int race){
        this.currentRace = race;
        FirstLine();
    }
    
    @Override
    public int getCurrentRace(){
        return currentRace;
    }
    
    @Override
    public int getDistance(){
        return distance;
    }
    
    @Override
    public synchronized void setDistance(int distance){
        this.distance = distance;
        FirstLine();
    }
    
    @Override
    public int getnHorses(){
        return nHorses;
    }
    
    @Override
    public void setnHorses(int nHorses) {
        this.nHorses = nHorses;
    }
    
    @Override
    public int getnSpectator(){
        return nSpectators;
    }
    
    @Override
    public void setnSpectator(int nSpectators) {
        this.nSpectators = nSpectators;
    }
    
    @Override
    public int getnRaces(){
        return nRaces;
    }
    
    @Override
    public void setnRaces(int nRaces) {
        this.nRaces = nRaces;
    }
    
    @Override
    public int gethorsePosition(int id) {
        return horsePositions.get(id);
    }
    
    @Override
    public synchronized void sethorsePositions(int id, int position) {
        horsePositions.put(id, position);
        FirstLine();
    }
    
    @Override
    public synchronized void setBetsPerSpectator(int ID, Bet bet) {
        betsPerSpectator.put(ID, bet);
        setSpectatorBet(ID,bet.getHorseID());
        setSpectatorMoney(ID,bet.getBetvalue());
    }
    
    @Override
    public Bet getBetsPerSpectator(int ID) {
        return betsPerSpectator.get(ID);
    }
    
    @Override
    public int getnWinners() {
        return nWinners;
    }

    @Override
    public void setnWinners(int size) {
        this.nWinners = size;
    }
    
    @Override
    public int getHorseWinnerID() {
        return horseWinner;
    }

    @Override
    public void setHorseWinnerID(int horseID) {
        this.horseWinner = horseID;
    }

    @Override
    public synchronized void setBrokerState(BrokerStates state) {
        BrokerState = state;
        FirstLine();        
    }

    @Override
    public synchronized void setSpectatorState(int spectID,SpectatorStates state) {
        statesSpectator[spectID-1] = state;
        FirstLine();        
    }    

    @Override
    public synchronized void setHorseState(int ID,HorseStates state,int move) {
        statesHorses[ID-1] = state;
        HorseAgility[ID-1] = move;
        FirstLine();
    }

    @Override
    public synchronized void setSpectatorBet(int spectID, int bet) {
        spectatorBet[spectID-1] = bet;
        FirstLine();
    }

    @Override
    public synchronized void setSpectatorMoney(int spectID, int money) {
        spectatorMoney[spectID-1] = money;
        FirstLine();
    }

    @Override
    public synchronized void setArrayPosition(HashMap<Integer,Integer> pos) {
        this.pos = pos;
        FirstLine();
    }

    @Override
    public synchronized void setCount(int horse, int contagem) {
        count[horse-1] = contagem;
    }

    @Override
    public int getHorseSkills(int horseID) {
        return HorseAgility[horseID];
    }

    @Override
    public void setHorseSkills(int horseID, int value) {
        HorseAgility[horseID] = value;
    }

}
