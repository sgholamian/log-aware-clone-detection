//,temp,TestBlockRecovery.java,594,613,temp,TestBlockRecovery.java,567,587
//,3
public class xxx {
  @Test(timeout=60000)
  public void testZeroLenReplicas() throws IOException, InterruptedException {
    if(LOG.isDebugEnabled()) {
      LOG.debug("Running " + GenericTestUtils.getMethodName());
    }
    doReturn(new ReplicaRecoveryInfo(block.getBlockId(), 0,
        block.getGenerationStamp(), ReplicaState.FINALIZED)).when(spyDN).
        initReplicaRecovery(any(RecoveringBlock.class));

    for(RecoveringBlock rBlock: initRecoveringBlocks()){
      BlockRecoveryWorker.RecoveryTaskContiguous RecoveryTaskContiguous =
          recoveryWorker.new RecoveryTaskContiguous(rBlock);
      BlockRecoveryWorker.RecoveryTaskContiguous spyTask
          = spy(RecoveryTaskContiguous);
      spyTask.recover();
    }
    DatanodeProtocol dnP = recoveryWorker.getActiveNamenodeForBP(POOL_ID);
    verify(dnP).commitBlockSynchronization(
        block, RECOVERY_ID, 0, true, true, DatanodeID.EMPTY_ARRAY, null);
  }

};