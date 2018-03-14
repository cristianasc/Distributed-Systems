package GeneralRepository;

public class Bet {

    public int SpectatorID;         // PunterID identifica um espectador
    public int horseID;             // HorseID identifica um cavalo
    public double Betvalue;         // Betvalue identifica um valor de uma aposta

    /**
     * Método para obter o ID de um apostador
     *
     * @return punterID - ID do apostador
     */
    public int getSpectatorID() {
        return SpectatorID;
    }

    /**
     * Método para atualizar o punterID de um apostador
     *
     * @param SpectatorID - ID do apostador
     */
    public void setSpectatorID(int SpectatorID) {
        this.SpectatorID = SpectatorID;
    }

    /**
     * Método para obter o ID de um cavalo
     *
     * @return horseID - ID do cavalo
     */
    public int getHorseID() {
        return horseID;
    }

    /**
     * Método para atualizar o horseID de um cavalo
     *
     * @param horseID - ID do cavalo
     */
    public void setHorseID(int horseID) {
        this.horseID = horseID;
    }

    /**
     * Método para obter o valor de uma aposta
     *
     * @return value - Valor da aposta
     */
    public double getBetvalue() {
        return Betvalue;
    }

    /**
     * Método para atualizar o valor de uma aposta
     *
     * @param Betvalue - Valor da apostas
     */
    public void setBetvalue(double Betvalue) {
        this.Betvalue = Betvalue;
    }
}