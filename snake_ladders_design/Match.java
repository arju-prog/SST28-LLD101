import java.util.Queue;

public class Match {
    private final GameBoard gameBoard;
    private final Queue<Participant> participants;
    private final RollingDie rollingDie;
    private final TurnStrategy turnStrategy;

    public Match(GameBoard gameBoard, Queue<Participant> participants, RollingDie rollingDie,
            TurnStrategy turnStrategy) {
        this.gameBoard = gameBoard;
        this.participants = participants;
        this.rollingDie = rollingDie;
        this.turnStrategy = turnStrategy;
    }

    public void initiateGame() {
        boolean matchFinished = false;

        while (!matchFinished && !participants.isEmpty()) {
            Participant current = participants.poll();

            matchFinished = turnStrategy.processTurn(current, gameBoard, rollingDie);

            if (matchFinished) {
                System.out.println(">>> " + current.getIdentifier() + " is the winner! <<<");
            } else {
                participants.offer(current);
            }
        }
    }
}
