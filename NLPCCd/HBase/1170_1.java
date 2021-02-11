//,temp,sample_2682.java,2,11,temp,sample_919.java,2,11
//,3
public class xxx {
protected void removeExcessFiles(ArrayList<HStoreFile> candidates, boolean isUserCompaction, boolean isMajorCompaction) {
int excess = candidates.size() - comConf.getMaxFilesToCompact();
if (excess > 0) {
if (isMajorCompaction && isUserCompaction) {


log.info("warning compacting more than files because of a user requested major compaction");
}
}
}

};