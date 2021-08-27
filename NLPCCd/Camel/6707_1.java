//,temp,sample_2830.java,2,14,temp,sample_8289.java,2,14
//,3
public class xxx {
public void handleFailedWrite(Exchange exchange, Exception exception) throws Exception {
loggedIn = false;
if (isStopping() || isStopped()) {
} else {
try {
disconnect();
} catch (Exception e) {


log.info("ignored exception during disconnect");
}
}
}

};