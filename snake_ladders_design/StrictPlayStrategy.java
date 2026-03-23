public class StrictPlayStrategy implements TurnStrategy {
    @Override
    public boolean processTurn(Participant p, GameBoard board, RollingDie die) {
        int continuousSixes = 0;
        boolean turnIsActive = true;

        while (turnIsActive) {
            int val = die.generateRoll();
            System.out.println(p.getIdentifier() + " obtained a " + val);

            if (val == 6) {
                continuousSixes++;
                if (continuousSixes == 3) {
                    System.out.println("Got three 6s in a row! Turn ends immediately.");
                    break;
                }
                System.out.println("Got a 6! You get another turn.");
                turnIsActive = true;
            } else {
                turnIsActive = false;
            }

            int targetPos = p.getCurrentSpot() + val;

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
