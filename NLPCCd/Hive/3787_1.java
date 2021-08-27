//,temp,sample_5387.java,2,14,temp,sample_5388.java,2,18
//,3
public class xxx {
protected TTransport connectWithRetry(int retries) throws HiveSQLException {
TTransportException exception = null;
for (int i = 0 ; i < retries; i++) {
try {
return connect(conf);
} catch (TTransportException e) {
exception = e;


log.info("connection attempt");
}
}
}

};