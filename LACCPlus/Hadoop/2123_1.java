//,temp,TestFiDataTransferProtocol2.java,235,243,temp,TestFiDataTransferProtocol2.java,207,215
//,2
public class xxx {
  @Test
  public void pipeline_Fi_22() throws IOException {
    final String methodName = FiTestUtil.getMethodName();
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final DataTransferTest t = (DataTransferTest) DataTransferTestUtil
        .initTest();
    initSlowDatanodeTest(t, new SleepAction(methodName, 2, MAX_SLEEP));
    writeSeveralPackets(methodName);
  }

};