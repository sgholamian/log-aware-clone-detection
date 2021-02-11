//,temp,sample_2773.java,2,11,temp,sample_2775.java,2,11
//,2
public class xxx {
public void commitMigration(NicProfile nic, Network network, VirtualMachineProfile vm, ReservationContext src, ReservationContext dst) {
try {
release(network, nic, vm, src);
} catch (ConcurrentOperationException e) {
} catch (ResourceUnavailableException e) {


log.info("commitmigration failed");
}
}

};