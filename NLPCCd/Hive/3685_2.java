//,temp,sample_3763.java,2,10,temp,sample_72.java,2,11
//,3
public class xxx {
private static void closeAndIgnoreExceptions(TezClient session) {
try {
session.stop();
} catch (SessionNotRunning nr) {
} catch (IOException | TezException ex) {


log.info("failed to close tez session after failure to initialize");
}
}

};