//,temp,sample_3080.java,2,12,temp,sample_5540.java,2,12
//,3
public class xxx {
public void checkDiskError() throws IOException {
Set<FsVolumeSpi> unhealthyVolumes;
try {
unhealthyVolumes = volumeChecker.checkAllVolumes(data);
lastDiskErrorCheck = Time.monotonicNow();
} catch (InterruptedException e) {


log.info("interruped while running disk check");
}
}

};