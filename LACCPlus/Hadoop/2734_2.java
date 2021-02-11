//,temp,TestBlockRecovery.java,567,587,temp,TestBlockRecovery.java,542,559
//,3
public class xxx {
  @Test(timeout=60000)
  public void testRecoveryInProgressException()
    throws IOException, InterruptedException {
    if(LOG.isDebugEnabled()) {
      LOG.debug("Running " + GenericTestUtils.getMethodName());
    }
    doThrow(new RecoveryInProgressException("Replica recovery is in progress")).
       when(spyDN).initReplicaRecovery(any(RecoveringBlock.class));

    for(RecoveringBlock rBlock: initRecoveringBlocks()){
      BlockRecoveryWorker.RecoveryTaskContiguous RecoveryTaskContiguous =
          recoveryWorker.new RecoveryTaskContiguous(rBlock);
      BlockRecoveryWorker.RecoveryTaskContiguous spyTask
          = spy(RecoveryTaskContiguous);
      spyTask.recover();
      verify(spyTask, never()).syncBlock(anyListOf(BlockRecord.class));
    }
  }

};