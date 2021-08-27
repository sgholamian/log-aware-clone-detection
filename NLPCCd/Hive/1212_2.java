//,temp,sample_5397.java,2,18,temp,sample_1160.java,2,13
//,3
public class xxx {
private void releaseResStream() {
try {
if (resStream != null) {
((FSDataInputStream) resStream).close();
resStream = null;
}
} catch (Exception e) {


log.info("exception while closing the resstream");
}
}

};