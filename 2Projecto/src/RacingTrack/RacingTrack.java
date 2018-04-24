/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RacingTrack;

import GeneralRepository.GeneralRepository;
import java.util.HashMap;

/**
 *
 * @author cristianacarvalho
 */
public class RacingTrack implements IRacingTrack_Horses, IRacingTrack_Broker{
    
    private boolean makeAMove;
    private boolean lastHorse;
    private final GeneralRepository gr;
    private HashMap<Integer, Integer> positions;
    private int next, nHorses, position, nHorsesInRace;
    
    /**
     * Construtor da classe
     * @param gr: General Repository
     */
    public RacingTrack(GeneralRepository gr){
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
    }

    /**
     * Método que indica se um cavalo já terminou ou não a corrida. Se ainda
     * estiver na lista de posições da corrida, então ainda não a terminou.
     * 
     * @param horseID
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
    }    
}
