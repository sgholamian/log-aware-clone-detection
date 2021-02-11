//,temp,sample_1540.java,2,18,temp,sample_1541.java,2,18
//,3
public class xxx {
public void dummy_method(){
try {
long seq = appendEntry(writer, TABLE_NAME, regionBytes, ("r" + editsCount.get()).getBytes(), regionBytes, QUALIFIER, VALUE, 0);
long count = editsCount.incrementAndGet();
LOG.info(getName() + " sync count=" + count + ", seq=" + seq);
try {
Thread.sleep(1);
} catch (InterruptedException e) {
}
} catch (IOException ex) {
if (ex instanceof RemoteException) {


log.info("juliet got remoteexception while writing");
}
}
}

};