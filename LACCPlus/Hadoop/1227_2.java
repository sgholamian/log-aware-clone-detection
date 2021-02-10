//,temp,TestFiDataTransferProtocol2.java,158,166,temp,TestFiDataTransferProtocol2.java,131,143
//,3
public class xxx {
  private void runTest17_19(String methodName, int dnIndex)
      throws IOException {
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final DataTransferTest t = (DataTransferTest) DataTransferTestUtil
        .initTest();
    initSlowDatanodeTest(t, new SleepAction(methodName, 0, 0, MAX_SLEEP));
    initSlowDatanodeTest(t, new SleepAction(methodName, 1, 0, MAX_SLEEP));
    initSlowDatanodeTest(t, new SleepAction(methodName, 2, 0, MAX_SLEEP));
    t.fiCallWritePacketToDisk.set(new CountdownDoosAction(methodName, dnIndex, 3));
    t.fiPipelineErrorAfterInit.set(new VerificationAction(methodName, dnIndex));
    writeSeveralPackets(methodName);
    Assert.assertTrue(t.isSuccess());
  }

};