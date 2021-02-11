//,temp,sample_4723.java,2,18,temp,sample_3873.java,2,13
//,3
public class xxx {
private void closeMobFileWriter(StoreFileWriter writer, long maxSeqId, long mobCellsCount) throws IOException {
if (writer != null) {
writer.appendMetadata(maxSeqId, false, mobCellsCount);
try {
writer.close();
} catch (IOException e) {


log.info("failed to close the writer of the file");
}
}
}

};