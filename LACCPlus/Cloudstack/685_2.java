//,temp,SspElement.java,575,584,temp,SspElement.java,564,573
//,2
public class xxx {
    @Override
    public void rollbackMigration(NicProfile nic, Network network, VirtualMachineProfile vm, ReservationContext src, ReservationContext dst) {
        try {
            release(network, nic, vm, dst);
        } catch (ConcurrentOperationException e) {
            s_logger.error("rollbackMigration failed.", e);
        } catch (ResourceUnavailableException e) {
            s_logger.error("rollbackMigration failed.", e);
        }
    }

};