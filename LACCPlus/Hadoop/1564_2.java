//,temp,TestBlockRecovery.java,314,349,temp,TestBlockRecovery.java,273,307
//,3
public class xxx {
  @Test
  public void testFinalizedRbwReplicas() throws IOException {
    if(LOG.isDebugEnabled()) {
      LOG.debug("Running " + GenericTestUtils.getMethodName());
    }
    
    // rbw and finalized replicas have the same length
    ReplicaRecoveryInfo replica1 = new ReplicaRecoveryInfo(BLOCK_ID, 
        REPLICA_LEN1, GEN_STAMP-1, ReplicaState.FINALIZED);
    ReplicaRecoveryInfo replica2 = new ReplicaRecoveryInfo(BLOCK_ID, 
        REPLICA_LEN1, GEN_STAMP-2, ReplicaState.RBW);

    InterDatanodeProtocol dn1 = mock(InterDatanodeProtocol.class);
    InterDatanodeProtocol dn2 = mock(InterDatanodeProtocol.class);

    testSyncReplicas(replica1, replica2, dn1, dn2, REPLICA_LEN1);
    verify(dn1).updateReplicaUnderRecovery(block, RECOVERY_ID, BLOCK_ID,
        REPLICA_LEN1);
    verify(dn2).updateReplicaUnderRecovery(block, RECOVERY_ID, BLOCK_ID,
        REPLICA_LEN1);
    
    // rbw replica has a different length from the finalized one
    replica1 = new ReplicaRecoveryInfo(BLOCK_ID, 
        REPLICA_LEN1, GEN_STAMP-1, ReplicaState.FINALIZED);
    replica2 = new ReplicaRecoveryInfo(BLOCK_ID, 
        REPLICA_LEN2, GEN_STAMP-2, ReplicaState.RBW);

    dn1 = mock(InterDatanodeProtocol.class);
    dn2 = mock(InterDatanodeProtocol.class);

    testSyncReplicas(replica1, replica2, dn1, dn2, REPLICA_LEN1);
    verify(dn1).updateReplicaUnderRecovery(block, RECOVERY_ID, BLOCK_ID, REPLICA_LEN1);
    verify(dn2, never()).updateReplicaUnderRecovery(
        block, RECOVERY_ID, BLOCK_ID, REPLICA_LEN1);
  }

};