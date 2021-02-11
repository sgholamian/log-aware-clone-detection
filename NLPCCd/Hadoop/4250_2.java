//,temp,sample_8262.java,2,12,temp,sample_7499.java,2,11
//,3
public class xxx {
protected void reportNextRecordRange(final TaskUmbilicalProtocol umbilical, long nextRecIndex) throws IOException{
long len = nextRecIndex - currentRecStartIndex +1;
SortedRanges.Range range = new SortedRanges.Range(currentRecStartIndex, len);
taskStatus.setNextRecordRange(range);
if (LOG.isDebugEnabled()) {


log.info("sending reportnextrecordrange");
}
}

};