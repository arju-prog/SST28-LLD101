public class Request {

    private final int targetFloor;
    private final Direction direction;

    public Request(int targetFloor, Direction direction) {
        this.targetFloor = targetFloor;
        this.direction = direction;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public Direction getDirection() {
        return direction;
    }
}
