//,temp,sample_6096.java,2,17,temp,sample_6095.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (!recoveryEnabled) {
return false;
}
boolean recoverySupportedByCommitter = isRecoverySupported();
if (!recoverySupportedByCommitter) {
return false;
}
int reducerCount = getConfig().getInt(MRJobConfig.NUM_REDUCES, 0);
boolean shuffleKeyValidForRecovery = TokenCache.getShuffleSecretKey(jobCredentials) != null;
if (reducerCount > 0 && !shuffleKeyValidForRecovery) {


log.info("not attempting to recover the shuffle key is invalid for recovery");
}
}

};