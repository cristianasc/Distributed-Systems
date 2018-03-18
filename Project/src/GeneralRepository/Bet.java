package GeneralRepository;

public class Bet {

    public int SpectatorID;         // SpectatorID identifica um espectador
    public int horseID;             // HorseID identifica um cavalo
    public double Betvalue;         // Betvalue identifica um valor de uma aposta

    
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

    
    public double getBetvalue() {
        return Betvalue;
    }

    public void setBetvalue(double Betvalue) {
        this.Betvalue = Betvalue;
    }
}