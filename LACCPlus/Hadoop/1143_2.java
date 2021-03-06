//,temp,TestFiDataTransferProtocol.java,131,139,temp,TestFiDataTransferProtocol.java,119,129
//,2
public class xxx {
  private static void runStatusReadTest(String methodName, int errorIndex,
      Action<DatanodeID, IOException> a) throws IOException {
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final DataTransferTest t = (DataTransferTest) DataTransferTestUtil
        .initTest();
    t.fiStatusRead.set(a);
    t.fiPipelineInitErrorNonAppend.set(new VerificationAction(methodName,
        errorIndex));
    write1byte(methodName);
    Assert.assertTrue(t.isSuccess());
  }

};