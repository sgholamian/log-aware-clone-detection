//,temp,sample_1571.java,2,12,temp,sample_2516.java,2,10
//,3
public class xxx {
protected void recoverableConnectIfNecessary() throws Exception {
try {
connectIfNecessary();
} catch (Exception e) {
if (log.isDebugEnabled()) {


log.info("could not connect to will try to recover");
}
}
}

};