//,temp,sample_5589.java,2,18,temp,sample_5590.java,2,16
//,3
public class xxx {
protected void preCommitWritersInternal() throws IOException {
if (LOG.isDebugEnabled()) {
LOG.debug("Stopping with " + cellsInCurrentWriter + " kvs in last writer" + ((this.sourceScanner == null) ? "" : ("; observed estimated " + this.sourceScanner.getEstimatedNumberOfKvsScanned() + " KVs total")));
}
if (lastCell != null) {
sanityCheckRight(right, lastCell);
}
if (existingWriters.isEmpty() && 1 == targetCount) {
if (LOG.isDebugEnabled()) {


log.info("merge expired stripes into one create an empty file to preserve metadata");
}
}
}

};