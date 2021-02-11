//,temp,sample_2773.java,2,11,temp,sample_2775.java,2,11
//,2
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