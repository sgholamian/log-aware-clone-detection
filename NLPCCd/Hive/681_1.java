//,temp,sample_5396.java,2,14,temp,sample_3784.java,2,12
//,3
public class xxx {
public synchronized void close() throws IOException {
if (!closed) {
closed = true;
Exception caughtException = null;
try {
din.close();
} catch (Exception err) {


log.info("error closing input stream");
}
}
}

};