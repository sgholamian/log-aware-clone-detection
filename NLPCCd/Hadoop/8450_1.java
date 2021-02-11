//,temp,sample_2184.java,2,12,temp,sample_8984.java,2,8
//,3
public class xxx {
public void writeData(HdfsDataOutputStream fos) throws IOException {
Preconditions.checkState(fos != null);
ByteBuffer dataBuffer;
try {
dataBuffer = getData();
} catch (Exception e1) {


log.info("failed to get request data offset count error");
}
}

};