//,temp,sample_2773.java,2,11,temp,sample_2772.java,2,10
//,3
public class xxx {
public void rollbackMigration(NicProfile nic, Network network, VirtualMachineProfile vm, ReservationContext src, ReservationContext dst) {
try {
release(network, nic, vm, dst);
} catch (ConcurrentOperationException e) {
} catch (ResourceUnavailableException e) {


log.info("rollbackmigration failed");
}
}

};