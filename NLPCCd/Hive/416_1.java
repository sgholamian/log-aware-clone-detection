//,temp,sample_5264.java,2,12,temp,sample_5263.java,2,12
//,3
public class xxx {
public void resetThreadName() {
final String sessionId = getSessionId();
final String logPrefix = getConf().getLogIdVar(sessionId);
final String currThreadName = Thread.currentThread().getName();
if (currThreadName.contains(logPrefix)) {
final String[] names = currThreadName.split(logPrefix);


log.info("resetting thread name to");
}
}

};