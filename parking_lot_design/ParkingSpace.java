package parking_system;

public class ParkingSpace {
    private String identifier;
    private SpaceSize sizeCategory;
    private boolean taken;
    private int level;
    private double posX, posY;

    public ParkingSpace(String id, SpaceSize size, int lvl, double x, double y) {
        this.identifier = id;
        this.sizeCategory = size;
        this.level = lvl;
        this.posX = x;
        this.posY = y;
        this.taken = false;
    }

    public String getIdentifier() {
        return identifier;
    }

    public SpaceSize getSizeCategory() {
        return sizeCategory;
    }

    public boolean isTaken() {
        return taken;
    }

    public void markTaken() {
        this.taken = true;
    }

    public void markFree() {
        this.taken = false;
    }

    public int getLevel() {
        return level;
    }

    public double computeDistanceTo(AccessPoint gate) {
        double deltaX = this.posX - gate.getPosX();
        double deltaY = this.posY - gate.getPosY();
        double deltaZ = (this.level - gate.getLevel()) * 10.0;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
}
