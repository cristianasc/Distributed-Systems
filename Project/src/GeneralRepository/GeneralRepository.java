/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralRepository;

import Broker.BrokerStates;
import Horse.HorseStates;
import Spectator.SpectatorStates;
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
public class GeneralRepository {
    private int nHorses, nSpectators, nRaces, nWinners, distance, horseWinner;
    private HashMap<Integer, Integer> horsePositions;
    private HashMap<Integer, Integer> horseSkills;
    private HashMap<Integer, Bet> betsPerSpectator;
    private int currentRace;
    
    private static PrintWriter pw;
    private final File log;
    private GeneralRepository gr;
    private BrokerStates BrokerState;
    private SpectatorStates[] statesSpectator;
    private int[] SpectatorMoney;
    //private int[] SpectatorMoney;
    private HorseStates[] statesHorses;
    private int[] HorseAgility;
    
    /**
     * Construtor da classe
     *
     * @param nHorses - Numero de cavalos
     * @param nRaces - Numero de corridas
     * @param distance - Distância da pista
     * @param betsPerSpectator - HashMap, com Key= ID do apostador e Value= valor
     * obtido no total de corridas
     */
    
    public GeneralRepository(int nHorses, int nSpectators, int nRaces, int distance){
        this.nHorses = nHorses;
        this.nSpectators = nSpectators;
        this.nRaces = nRaces;
        this.distance = distance;
        this.horsePositions = new HashMap<>();
        this.horseSkills = new HashMap<>();
        this.betsPerSpectator = new HashMap<>();
        this.currentRace = 0;
        
        
        this.gr = gr;
        this.BrokerState = BrokerStates.OPENING_THE_EVENT;
        this.statesSpectator = new SpectatorStates[nSpectators];
        this.statesHorses = new HorseStates[nHorses];
        this.SpectatorMoney = new int[nSpectators];
        this.HorseAgility = new int[nHorses];
        
        
        for (int i = 0; i < nHorses; i++) {
            statesHorses[i] = HorseStates.AT_THE_STABLE;
        }
        
        for (int i = 0; i < nSpectators; i++) {
            statesSpectator[i] = SpectatorStates.WAITING_FOR_A_RACE_TO_START;
        }
        
        for (int i = 0; i < SpectatorMoney.length; i++) {
            SpectatorMoney[i] = 0;            
        }
        
        for (int i = 0; i < nHorses; i++) {
            HorseAgility[i] = 0;            
        }

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
        String filename = "assignment1T1G7_" + date.format(today) + ".txt";

        this.log = new File(filename);
        writeInit();
          
    }
    
    public synchronized void FirstLine(){
        
        pw.printf("\t%4s\t", BrokerState.toString());
        
        System.err.println(BrokerState);
        
        for (int i=0; i < nSpectators; i++){
            if (statesSpectator[i] != null) {
                pw.printf("%3s %3d   ", statesSpectator[i].toString(), SpectatorMoney[i]);
            }            
        }
        pw.printf("%2d   ", currentRace);  
        for (int i=0; i < nHorses; i++){
            if (statesHorses[i] != null) {
                pw.printf("%3s  %4d ",  statesHorses[i].toString(), HorseAgility[i]);
            }            
        }
        pw.println();
        pw.flush();
        
        //SecondLine();
    }
    /*
    public synchronized void SecondLine(){
        
        pw.printf("\t%4s\t", BrokerState.toString());
        System.err.println(BrokerState);
        
        for (int i=0; i < nSpectators; i++){
            if (statesSpectator[i] != null) {
                pw.printf(" %3s %3d   ", statesSpectator[i].toString(), SpectatorMoney[i]);
            }            
        }
        
        for (int i=0; i < nHorses; i++){
            if (statesHorses[i] != null) {
                pw.printf(" %3s  %4d ",  statesHorses[i].toString(), HorseAgility[i]);
            }            
        }
        pw.println();
        pw.flush();
    }*/
    
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
            pw.print("\tRn  Dist");
            for (int i=0; i < nSpectators; i++){
                pw.printf(" BS%d BA%d Od%d N%d Ps%d SD%d",i,i,i,i,i,i);
            }
            pw.println();            
            pw.flush();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setActualRace(int race){
        this.currentRace = race;
    }
    
    public int getCurrentRace(){
        return currentRace;
    }
    
    public int getDistance(){
        return distance;
    }
    
    public void setDistance(){
        this.distance = distance;
    }
    
    public int getnHorses(){
        return nHorses;
    }
    
    public void setnHorses(int nHorses) {
        this.nHorses = nHorses;
    }
    
    public int getnSpectator(){
        return nSpectators;
    }
    
    public void setnSpectator(int nSpectators) {
        this.nSpectators = nSpectators;
    }
    
    public int getnRaces(){
        return nRaces;
    }
    
    public void setnRaces(int nRaces) {
        this.nRaces = nRaces;
    }
    
    public int getHorseSkills(int id) {
        return horseSkills.get(id);
    }
    
    public void setHorseSkills(int id, int skiil) {
        horseSkills.put(id, skiil);
    }
    
    public void gethorsePositions(int id) {
        horsePositions.get(id);
    }
    
    public void sethorsePositions(int id, int position) {
        horsePositions.put(id, position);
    }
    
    public void setBetsPerSpectator(int ID, Bet bet) {
        betsPerSpectator.put(ID, bet);
    }
    
    public Bet getBetsPerSpectator(int ID) {
        return betsPerSpectator.get(ID);
    }
    
    public int getnWinners() {
        return nWinners;
    }

    public void setnWinners(int size) {
        this.nWinners = size;
    }
    
    public int getHorseWinner() {
        return horseWinner;
    }

    public void setHorseWinner(int horseID) {
        this.horseWinner = horseID;
    }

    public void setBrokerState(BrokerStates state) {
        BrokerState = state;
        FirstLine();        
    }

    public void setSpectatorState(int spectID,SpectatorStates state,int money) {
        statesSpectator[spectID-1] = state;
        SpectatorMoney[spectID-1] = money;
        FirstLine();        
    }    

    public void setHorseState(int ID,HorseStates state,int move) {
        statesHorses[ID-1] = state;
        HorseAgility[ID-1] = move;
        FirstLine();
    }
}
