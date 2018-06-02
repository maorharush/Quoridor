package quoridor.dao.dbo;

public class GameDBO extends DBO {
    private String whichAI;


    public String getWhichAI() {
        return whichAI;
    }

    public void setWhichAI(String whichAI) {
        this.whichAI = whichAI;
    }

    @Override
    public String toString() {
        return "GameDBO{" +
                "whichAI='" + whichAI + '\'' +
                '}';
    }
}
