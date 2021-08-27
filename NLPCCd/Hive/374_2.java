//,temp,sample_749.java,9,17,temp,sample_1025.java,2,9
//,3
public class xxx {
public void close(TaskAttemptContext context) throws IOException, InterruptedException {
for (Entry<String, BaseRecordWriterContainer> entry : baseRecordWriters.entrySet()) {
BaseRecordWriterContainer baseRWContainer = entry.getValue();


log.info("closing record writer for alias");
}
}

};