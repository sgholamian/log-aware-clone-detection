//,temp,sample_5264.java,2,12,temp,sample_5263.java,2,12
//,3
public class xxx {
public void updateThreadName() {
final String sessionId = getSessionId();
final String logPrefix = getConf().getLogIdVar(sessionId);
final String currThreadName = Thread.currentThread().getName();
if (!currThreadName.contains(logPrefix)) {
final String newThreadName = logPrefix + " " + currThreadName;


log.info("updating thread name to");
}
}

};