/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitors.GeneralRepository;

public class Bet {

    private int SpectatorID;       // SpectatorID identifica um espectador
    private int horseID;           // HorseID identifica um cavalo
    private int Betvalue;         // Betvalue identifica um valor de uma aposta

    /**
     * Método para obter o ID de um espectador/apostador
     * @return SpectatorID: ID do espectador/apostador
     */
    public int getSpectatorID() {
        return SpectatorID;
    }

    /**
     * Método para alterar o ID de um espetador/apostador
     * @param SpectatorID: ID do espetador
     */
    public void setSpectatorID(int SpectatorID) {
        this.SpectatorID = SpectatorID;
    }
    
    /**
     * Método para obter o ID de cavalo
     * @return horseID: ID do cavalo
     */
    public int getHorseID() {
        return horseID;
    }
    
    /**
     * Método para alterar o ID de um cavalo
     * @param horseID: ID do cavalo
     */
    public void setHorseID(int horseID) {
        this.horseID = horseID;
    }
    
    /**
     * Método para obter o valor de uma bet
     * @return Betvalue: valor de uma bet
     */
    public int getBetvalue() {
        return Betvalue;
    }

    /**
     * Método para alterar o ID de uma bet
     * @param Betvalue: valor de uma bet
     */
    public void setBetvalue(int Betvalue) {
        this.Betvalue = Betvalue;
    }
}