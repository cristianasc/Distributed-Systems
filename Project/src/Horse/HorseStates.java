/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Horse;

/**
 *
 * @author cristianacarvalho
 */
public enum HorseStates {
    AT_THE_STABLE("STB"), AT_THE_PADDOCK("PDK"), AT_THE_START_LINE("ASL"), RUNNING("RUN"), AT_THE_FINISH_LINE("AFL");
    private String state;
    
    private HorseStates(String state){
        this.state = state;
    }
    
    @Override
    public String toString(){
        return state;
    }
}
