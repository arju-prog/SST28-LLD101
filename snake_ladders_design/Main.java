import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Snake> builtSnakes = Arrays.asList(
                new Snake(16, 6),
                new Snake(47, 26),
                new Snake(49, 11),
                new Snake(99, 1));

        List<Staircase> builtStaircases = Arrays.asList(
                new Staircase(1, 38),
                new Staircase(4, 14),
                new Staircase(21, 42),
                new Staircase(80, 98));

        List<String> partNames = Arrays.asList("Max Verstappen", "Charles Leclerc");

        Match currentMatch = MatchBuilder.setupMatch(partNames, builtSnakes, builtStaircases, "STANDARD");
        currentMatch.initiateGame();
    }
}