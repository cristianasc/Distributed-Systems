package GeneralRepository;

public class Bet {

    private int SpectatorID;       // SpectatorID identifica um espectador
    private int horseID;           // HorseID identifica um cavalo
    private int Betvalue;         // Betvalue identifica um valor de uma aposta

    public int getSpectatorID() {
        return SpectatorID;
    }

    public void setSpectatorID(int SpectatorID) {
        this.SpectatorID = SpectatorID;
    }
    
    public int getHorseID() {
        return horseID;
    }
    
    public void setHorseID(int horseID) {
        this.horseID = horseID;
    }
    
    public int getBetvalue() {
        return Betvalue;
    }

    public void setBetvalue(int Betvalue) {
        this.Betvalue = Betvalue;
    }
}