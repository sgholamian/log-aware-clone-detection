//,temp,sample_8599.java,2,10,temp,sample_3080.java,2,12
//,3
public class xxx {
public Set<FsVolumeSpi> checkAllVolumes( final FsDatasetSpi<? extends FsVolumeSpi> dataset) throws InterruptedException {
final long gap = timer.monotonicNow() - lastAllVolumesCheck;
if (gap < minDiskCheckGapMs) {
numSkippedChecks.incrementAndGet();


log.info("skipped checking all volumes time since last check is less than the minimum gap between checks ms");
}
}

};