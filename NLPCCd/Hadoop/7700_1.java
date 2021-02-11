//,temp,sample_5435.java,2,16,temp,sample_9605.java,2,13
//,3
public class xxx {
public synchronized void joinElection(byte[] data) throws HadoopIllegalArgumentException {
if (data == null) {
throw new HadoopIllegalArgumentException("data cannot be null");
}
if (wantToBeInElection) {
return;
}
appData = new byte[data.length];
System.arraycopy(data, 0, appData, 0, data.length);
if (LOG.isDebugEnabled()) {


log.info("attempting active election for");
}
}

};