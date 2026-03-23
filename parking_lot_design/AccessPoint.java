package parking_system;

public class AccessPoint {
    private String identifier;
    private int level;
    private double posX, posY;

    public AccessPoint(String id, int level, double x, double y) {
        this.identifier = id;
        this.level = level;
        this.posX = x;
        this.posY = y;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getLevel() {
        return level;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
