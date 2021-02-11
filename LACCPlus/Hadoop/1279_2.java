//,temp,TestFiDataTransferProtocol.java,131,139,temp,TestFiDataTransferProtocol2.java,158,166
//,3
public class xxx {
  private void runTest34_35(String methodName, int dnIndex) throws IOException {
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final DataTransferTest t = (DataTransferTest) DataTransferTestUtil
        .initTest();
    t.fiAfterDownstreamStatusRead.set(new CountdownSleepAction(methodName, dnIndex, 0, 3));
    t.fiPipelineErrorAfterInit.set(new VerificationAction(methodName, dnIndex));
    writeSeveralPackets(methodName);
    Assert.assertTrue(t.isSuccess());
  }

};