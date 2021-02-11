//,temp,sample_2774.java,2,10,temp,sample_2772.java,2,10
//,2
public class xxx {
public void commitMigration(NicProfile nic, Network network, VirtualMachineProfile vm, ReservationContext src, ReservationContext dst) {
try {
release(network, nic, vm, src);
} catch (ConcurrentOperationException e) {


log.info("commitmigration failed");
}
}

};