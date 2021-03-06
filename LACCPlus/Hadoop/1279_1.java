//,temp,TestFiDataTransferProtocol.java,131,139,temp,TestFiDataTransferProtocol2.java,158,166
//,3
public class xxx {
  private static void runCallWritePacketToDisk(String methodName,
      int errorIndex, Action<DatanodeID, IOException> a) throws IOException {
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final DataTransferTest t = (DataTransferTest)DataTransferTestUtil.initTest();
    t.fiCallWritePacketToDisk.set(a);
    t.fiPipelineErrorAfterInit.set(new VerificationAction(methodName, errorIndex));
    write1byte(methodName);
    Assert.assertTrue(t.isSuccess());
  }

};