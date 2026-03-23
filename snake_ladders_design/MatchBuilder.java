import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MatchBuilder {
    public static Match setupMatch(List<String> userNames, List<Chute> chutes,
            List<Staircase> staircases, String diffMode) {
        GameBoard gb = new GameBoard(100, chutes, staircases);
        RollingDie die = new RollingDie(6);

        Queue<Participant> pQueue = new LinkedList<>();
        for (String nm : userNames) {
            pQueue.offer(new Participant(nm));
        }

        TurnStrategy strat = "STRICT".equalsIgnoreCase(diffMode)
                ? new StrictPlayStrategy()
                : new StandardPlayStrategy();

        return new Match(gb, pQueue, die, strat);
    }
}
