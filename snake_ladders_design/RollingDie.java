import java.security.SecureRandom;

public class RollingDie {
    private final int maxFaces;
    private final SecureRandom rng;

    public RollingDie(int maxFaces) {
        this.maxFaces = maxFaces;
        this.rng = new SecureRandom();
    }

    public int generateRoll() {
        return rng.nextInt(maxFaces) + 1;
    }
}
