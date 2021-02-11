//,temp,SspElement.java,575,584,temp,SspGuestNetworkGuru.java,131,143
//,3
public class xxx {
    @Override
    public void commitMigration(NicProfile nic, Network network, VirtualMachineProfile vm, ReservationContext src, ReservationContext dst) {
        try {
            release(network, nic, vm, src);
        } catch (ConcurrentOperationException e) {
            s_logger.error("commitMigration failed.", e);
        } catch (ResourceUnavailableException e) {
            s_logger.error("commitMigration failed.", e);
        }
    }

};