import java.util.List;

public class GameBoard {
    private final int boardSize;
    private final List<Chute> chutes;
    private final List<Staircase> staircases;

    public GameBoard(int boardSize, List<Chute> chutes, List<Staircase> staircases) {
        this.boardSize = boardSize;
        this.chutes = chutes;
        this.staircases = staircases;
    }

    public int fetchSize() {
        return this.boardSize;
    }

    public int calculateEffectivePosition(int currentPos) {
        for (Chute chute : chutes) {
            if (chute.getTop() == currentPos) {
                return chute.getBottom();
            }
        }

        for (Staircase staircase : staircases) {
            if (staircase.getBase() == currentPos) {
                return staircase.getTop();
            }
        }

        return currentPos;
    }
}
