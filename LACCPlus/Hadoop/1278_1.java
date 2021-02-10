//,temp,TestFiDataTransferProtocol.java,107,117,temp,TestFiDataTransferProtocol2.java,158,166
//,3
public class xxx {
  private static void runReceiverOpWriteBlockTest(String methodName,
      int errorIndex, Action<DatanodeID, IOException> a) throws IOException {
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final DataTransferTest t = (DataTransferTest) DataTransferTestUtil
        .initTest();
    t.fiReceiverOpWriteBlock.set(a);
    t.fiPipelineInitErrorNonAppend.set(new VerificationAction(methodName,
        errorIndex));
    write1byte(methodName);
    Assert.assertTrue(t.isSuccess());
  }

};