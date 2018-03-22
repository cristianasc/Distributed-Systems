package GeneralRepository;

public class Bet {

    public int SpectatorID;       // SpectatorID identifica um espectador
    public int horseID;           // HorseID identifica um cavalo
    public int Betvalue;         // Betvalue identifica um valor de uma aposta

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