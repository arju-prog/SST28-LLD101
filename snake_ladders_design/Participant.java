public class Participant {
    private String identifier;
    private int currentSpot;

    public Participant(String identifier) {
        this.identifier = identifier;
        this.currentSpot = 0;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getCurrentSpot() {
        return currentSpot;
    }

    public void updateSpot(int newSpot) {
        this.currentSpot = newSpot;
    }
}
