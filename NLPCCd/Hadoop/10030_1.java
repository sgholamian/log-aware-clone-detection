//,temp,sample_6096.java,2,17,temp,sample_6095.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (!recoverySupportedByCommitter) {
return false;
}
int reducerCount = getConfig().getInt(MRJobConfig.NUM_REDUCES, 0);
boolean shuffleKeyValidForRecovery = TokenCache.getShuffleSecretKey(jobCredentials) != null;
if (reducerCount > 0 && !shuffleKeyValidForRecovery) {
return false;
}
boolean spillEncrypted = CryptoUtils.isEncryptedSpillEnabled(getConfig());
if (reducerCount > 0 && spillEncrypted) {


log.info("not attempting to recover intermediate spill encryption is enabled");
}
}

};