//,temp,TestFiDataTransferProtocol.java,107,117,temp,TestFiDataTransferProtocol.java,97,105
//,3
public class xxx {
  private static void runSlowDatanodeTest(String methodName, SleepAction a
                  ) throws IOException {
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final DataTransferTest t = (DataTransferTest)DataTransferTestUtil.initTest();
    t.fiCallReceivePacket.set(a);
    t.fiReceiverOpWriteBlock.set(a);
    t.fiStatusRead.set(a);
    write1byte(methodName);
  }

};