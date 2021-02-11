//,temp,sample_1214.java,2,17,temp,sample_1213.java,2,17
//,2
public class xxx {
private void shutdownWAL(final boolean close) {
if (this.walFactory != null) {
try {
if (close) {
walFactory.close();
} else {
walFactory.shutdown();
}
} catch (Throwable e) {
e = e instanceof RemoteException ? ((RemoteException) e).unwrapRemoteException() : e;


log.info("shutdown close exception details");
}
}
}

};