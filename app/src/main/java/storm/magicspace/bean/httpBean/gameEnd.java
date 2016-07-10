package storm.magicspace.bean.httpBean;


import java.io.Serializable;

public class gameEnd implements Serializable {
    public String contentId;
    public int duration;
    public boolean isWon;

    public void gameEnd() {

    }

    public gameEnd(String contentId, int duration, boolean isWon) {
        this.contentId = contentId;
        this.duration = duration;
        this.isWon = isWon;
    }
}
