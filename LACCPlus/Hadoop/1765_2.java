//,temp,TestFiHFlush.java,46,57,temp,TestFiDataTransferProtocol.java,107,117
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