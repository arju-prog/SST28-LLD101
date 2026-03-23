public interface TurnStrategy {
    boolean processTurn(Participant participant, GameBoard board, RollingDie die);
}
