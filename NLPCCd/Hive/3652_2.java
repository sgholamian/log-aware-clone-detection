//,temp,sample_4180.java,2,12,temp,sample_4181.java,2,13
//,3
public class xxx {
private void doDelete() throws Exception {
int nDeletedTokens = 0;
List<DelegationTokenIdentifier> allDelegationTokenIDs = getAllDelegationTokenIDs();
for (DelegationTokenIdentifier tokenId : Iterables.filter(allDelegationTokenIDs, selectForDeletion)) {
if ((++nDeletedTokens % batchSize) == 0) {
try {Thread.sleep(sleepTimeMillis); } catch (InterruptedException ignore) {}
}


log.info("deleting token");
}
}

};