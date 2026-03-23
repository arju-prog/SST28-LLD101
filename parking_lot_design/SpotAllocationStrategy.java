package parking_system;

import java.util.List;

public interface SpotAllocationStrategy {
    ParkingSpace locateBestSpot(AccessPoint entry, List<ParkingSpace> available, AutoCategory type) throws Exception;
}
