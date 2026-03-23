import java.util.List;

public class GameBoard {
    private final int boardSize;
    private final List<Snake> snakes;
    private final List<Staircase> staircases;

    public GameBoard(int boardSize, List<Snake> snakes, List<Staircase> staircases) {
        this.boardSize = boardSize;
        this.snakes = snakes;
        this.staircases = staircases;
    }

    public int fetchSize() {
        return this.boardSize;
    }

    public int calculateEffectivePosition(int currentPos) {
        for (Snake snake : snakes) {
            if (snake.getTop() == currentPos) {
                return snake.getBottom();
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
