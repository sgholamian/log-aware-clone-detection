//,temp,TestBlockRecovery.java,481,495,temp,TestBlockRecovery.java,462,474
//,3
public class xxx {
  @Test
  public void testErrorReplicas() throws IOException, InterruptedException {
    if(LOG.isDebugEnabled()) {
      LOG.debug("Running " + GenericTestUtils.getMethodName());
    }
    DataNode spyDN = spy(dn);
    doThrow(new IOException()).
       when(spyDN).initReplicaRecovery(any(RecoveringBlock.class));
    Daemon d = spyDN.recoverBlocks("fake NN", initRecoveringBlocks());
    d.join();
    verify(spyDN, never()).syncBlock(
        any(RecoveringBlock.class), anyListOf(BlockRecord.class));
  }

};