//,temp,sample_2178.java,2,12,temp,sample_5118.java,2,15
//,3
public class xxx {
private void keyComplete(String key) {
Preconditions.checkNotNull(key, "Key must be specified");
boolean removed = knownAppenders.remove(key);
if (removed) {
if (LOGGER.isDebugEnabled()) {


log.info("deleting appender for key");
}
}
}

};