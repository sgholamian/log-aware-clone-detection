//,temp,sample_4507.java,2,12,temp,sample_1572.java,2,15
//,3
public class xxx {
protected void recoverableConnectIfNecessary() throws Exception {
try {
connectIfNecessary();
} catch (Exception e) {
if (log.isDebugEnabled()) {
}
loggedIn = false;
}
if (!loggedIn) {


log.info("trying to recover connection to with a fresh client");
}
}

};