//,temp,sample_7977.java,2,7,temp,sample_4301.java,2,13
//,3
public class xxx {
public synchronized void addPersistedDelegationToken( TokenIdent identifier, long renewDate) throws IOException {
if (running) {
throw new IOException( "Can't add persisted delegation token to a running SecretManager.");
}
int keyId = identifier.getMasterKeyId();
DelegationKey dKey = allKeys.get(keyId);
if (dKey == null) {


log.info("no key found for persisted identifier");
}
}

};