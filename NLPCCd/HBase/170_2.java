//,temp,sample_4923.java,2,8,temp,sample_2684.java,2,10
//,3
public class xxx {
protected ArrayList<HStoreFile> checkMinFilesCriteria(ArrayList<HStoreFile> candidates, int minFiles) {
if (candidates.size() < minFiles) {
if (LOG.isDebugEnabled()) {


log.info("not compacting files because we only have files ready for compaction need to initiate");
}
}
}

};