//,temp,TestBlockRecovery.java,782,811,temp,TestBlockRecovery.java,594,613
//,3
public class xxx {
  @Test(timeout=60000)
  public void testRURReplicas() throws Exception {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Running " + GenericTestUtils.getMethodName());
    }

    doReturn(new ReplicaRecoveryInfo(block.getBlockId(), block.getNumBytes(),
        block.getGenerationStamp(), ReplicaState.RUR)).when(spyDN).
        initReplicaRecovery(any(RecoveringBlock.class));

    boolean exceptionThrown = false;
    try {
      for (RecoveringBlock rBlock : initRecoveringBlocks()) {
        BlockRecoveryWorker.RecoveryTaskContiguous RecoveryTaskContiguous =
            recoveryWorker.new RecoveryTaskContiguous(rBlock);
        BlockRecoveryWorker.RecoveryTaskContiguous spyTask =
            spy(RecoveryTaskContiguous);
        spyTask.recover();
      }
    } catch (IOException e) {
      // expect IOException to be thrown here
      e.printStackTrace();
      assertTrue("Wrong exception was thrown: " + e.getMessage(),
          e.getMessage().contains("Found 1 replica(s) for block " + block +
          " but none is in RWR or better state"));
      exceptionThrown = true;
    } finally {
      assertTrue(exceptionThrown);
    }
  }

};