//,temp,PropertiesStorage.java,68,80,temp,SspGuestNetworkGuru.java,131,143
//,3
public class xxx {
    @Override
    public boolean prepareMigration(NicProfile nic, Network network, VirtualMachineProfile vm, DeployDestination dest, ReservationContext context) {
        try {
            reserve(nic, network, vm, dest, context);
        } catch (InsufficientVirtualNetworkCapacityException e) {
            s_logger.error("prepareForMigration failed", e);
            return false;
        } catch (InsufficientAddressCapacityException e) {
            s_logger.error("prepareForMigration failed", e);
            return false;
        }
        return true;
    }

};