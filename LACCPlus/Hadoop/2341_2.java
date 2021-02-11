//,temp,TestBlockRecovery.java,594,613,temp,TestBlockRecovery.java,567,587
//,3
public class xxx {
  @Test(timeout=60000)
  public void testErrorReplicas() throws IOException, InterruptedException {
    if(LOG.isDebugEnabled()) {
      LOG.debug("Running " + GenericTestUtils.getMethodName());
    }
    doThrow(new IOException()).
       when(spyDN).initReplicaRecovery(any(RecoveringBlock.class));

    for(RecoveringBlock rBlock: initRecoveringBlocks()){
      BlockRecoveryWorker.RecoveryTaskContiguous RecoveryTaskContiguous =
          recoveryWorker.new RecoveryTaskContiguous(rBlock);
      BlockRecoveryWorker.RecoveryTaskContiguous spyTask = spy(RecoveryTaskContiguous);
      try {
        spyTask.recover();
        fail();
      } catch(IOException e){
        GenericTestUtils.assertExceptionContains("All datanodes failed", e);
      }
      verify(spyTask, never()).syncBlock(anyListOf(BlockRecord.class));
    }
  }

};