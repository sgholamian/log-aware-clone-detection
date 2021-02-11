//,temp,sample_2392.java,2,12,temp,sample_3280.java,2,12
//,3
public class xxx {
public void run() {
try (FsVolumeReference ref = v.obtainReference()) {
long startTime = Time.monotonicNow();
v.getVolumeMap(bpid, volumeMap, ramDiskReplicaMap);
long timeTaken = Time.monotonicNow() - startTime;
} catch (ClosedChannelException e) {


log.info("the volume is closed while adding replicas ignored");
}
}

};