//,temp,TestBlockRecovery.java,481,495,temp,TestBlockRecovery.java,441,454
//,3
public class xxx {
  @Test
  public void testZeroLenReplicas() throws IOException, InterruptedException {
    if(LOG.isDebugEnabled()) {
      LOG.debug("Running " + GenericTestUtils.getMethodName());
    }
    DataNode spyDN = spy(dn);
    doReturn(new ReplicaRecoveryInfo(block.getBlockId(), 0,
        block.getGenerationStamp(), ReplicaState.FINALIZED)).when(spyDN).
        initReplicaRecovery(any(RecoveringBlock.class));
    Daemon d = spyDN.recoverBlocks("fake NN", initRecoveringBlocks());
    d.join();
    DatanodeProtocol dnP = dn.getActiveNamenodeForBP(POOL_ID);
    verify(dnP).commitBlockSynchronization(
        block, RECOVERY_ID, 0, true, true, DatanodeID.EMPTY_ARRAY, null);
  }

};