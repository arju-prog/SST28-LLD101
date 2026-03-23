public class StandardPlayStrategy implements TurnStrategy {
    @Override
    public boolean processTurn(Participant p, GameBoard board, RollingDie die) {
        boolean activeTurn = true;

        while (activeTurn) {
            int obtainedValue = die.generateRoll();
            System.out.println(p.getIdentifier() + " obtained a " + obtainedValue);

            if (obtainedValue == 6) {
                System.out.println("Got a 6! You get another turn.");
                activeTurn = true;
            } else {
                activeTurn = false;
            }

            int targetPos = p.getCurrentSpot() + obtainedValue;

            if (targetPos > board.fetchSize()) {
                continue;
            }

            targetPos = board.calculateEffectivePosition(targetPos);
            p.updateSpot(targetPos);
            System.out.println(p.getIdentifier() + " relocated to " + targetPos);

            if (p.getCurrentSpot() == board.fetchSize()) {
                return true;
            }
        }
        return false;
    }
}
