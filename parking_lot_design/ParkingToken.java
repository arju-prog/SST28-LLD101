package parking_system;

import java.util.UUID;

public class ParkingToken {
    private String tokenId;
    private Automobile auto;
    private ParkingSpace assignedSpace;
    private long checkInTime;

    public ParkingToken(Automobile car, ParkingSpace space) {
        this.tokenId = UUID.randomUUID().toString().substring(0, 8);
        this.auto = car;
        this.assignedSpace = space;
        this.checkInTime = System.currentTimeMillis();
    }

    public String getTokenId() {
        return tokenId;
    }

    public Automobile getAuto() {
        return auto;
    }

    public ParkingSpace getAssignedSpace() {
        return assignedSpace;
    }

    public long getCheckInTime() {
        return checkInTime;
    }

    public void alterCheckInTime(long mockTime) {
        this.checkInTime = mockTime;
    }
}
