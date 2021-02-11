//,temp,sample_3082.java,2,17,temp,sample_3081.java,2,15
//,3
public class xxx {
public void dummy_method(){
Set<FsVolumeSpi> unhealthyVolumes;
try {
unhealthyVolumes = volumeChecker.checkAllVolumes(data);
lastDiskErrorCheck = Time.monotonicNow();
} catch (InterruptedException e) {
throw new IOException("Interrupted while running disk check", e);
}
if (unhealthyVolumes.size() > 0) {
handleVolumeFailures(unhealthyVolumes);
} else {


log.info("checkdiskerror encountered no failures");
}
}

};