//,temp,TestFiPipelineClose.java,207,214,temp,TestFiPipelineClose.java,38,45
//,2
public class xxx {
  private static void runPipelineCloseTest(String methodName,
      Action<DatanodeID, IOException> a) throws IOException {
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final DataTransferTest t = (DataTransferTest) DataTransferTestUtil
        .initTest();
    t.fiPipelineClose.set(a);
    TestFiDataTransferProtocol.write1byte(methodName);
  }

};