//,temp,TestFiDataTransferProtocol2.java,235,243,temp,TestFiDataTransferProtocol2.java,221,229
//,2
public class xxx {
  @Test
  public void pipeline_Fi_21() throws IOException {
    final String methodName = FiTestUtil.getMethodName();
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final DataTransferTest t = (DataTransferTest) DataTransferTestUtil
        .initTest();
    initSlowDatanodeTest(t, new SleepAction(methodName, 1, MAX_SLEEP));
    writeSeveralPackets(methodName);
  }

};