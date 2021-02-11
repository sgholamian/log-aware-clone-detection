//,temp,TestBlockRecovery.java,380,398,temp,TestBlockRecovery.java,356,373
//,3
public class xxx {
  @Test
  public void testRBWReplicas() throws IOException {
    if(LOG.isDebugEnabled()) {
      LOG.debug("Running " + GenericTestUtils.getMethodName());
    }
    ReplicaRecoveryInfo replica1 = new ReplicaRecoveryInfo(BLOCK_ID, 
        REPLICA_LEN1, GEN_STAMP-1, ReplicaState.RBW);
    ReplicaRecoveryInfo replica2 = new ReplicaRecoveryInfo(BLOCK_ID, 
        REPLICA_LEN2, GEN_STAMP-2, ReplicaState.RBW);

    InterDatanodeProtocol dn1 = mock(InterDatanodeProtocol.class);
    InterDatanodeProtocol dn2 = mock(InterDatanodeProtocol.class);

    long minLen = Math.min(REPLICA_LEN1, REPLICA_LEN2);
    testSyncReplicas(replica1, replica2, dn1, dn2, minLen);
    verify(dn1).updateReplicaUnderRecovery(block, RECOVERY_ID, BLOCK_ID, minLen);
    verify(dn2).updateReplicaUnderRecovery(block, RECOVERY_ID, BLOCK_ID, minLen);
  }

};