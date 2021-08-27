//,temp,sample_942.java,2,19,temp,sample_941.java,2,12
//,3
public class xxx {
public long getEstimatedSize(InputSplit inputSplit) throws IOException {
long colProjSize = inputSplit.getLength();
if (inputSplit instanceof ColumnarSplit) {
colProjSize = ((ColumnarSplit) inputSplit).getColumnarProjectionSize();
if (LOG.isDebugEnabled()) {


log.info("estimated column projection size");
}
}
}

};