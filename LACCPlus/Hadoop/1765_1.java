//,temp,TestFiHFlush.java,46,57,temp,TestFiDataTransferProtocol.java,107,117
//,3
public class xxx {
  private static void runDiskErrorTest (final Configuration conf, 
      final String methodName, final int block_size, DerrAction a, int index,
      boolean trueVerification)
      throws IOException {
    FiTestUtil.LOG.info("Running " + methodName + " ...");
    final HFlushTest hft = (HFlushTest) FiHFlushTestUtil.initTest();
    hft.fiCallHFlush.set(a);
    hft.fiErrorOnCallHFlush.set(new DataTransferTestUtil.VerificationAction(methodName, index));
    TestHFlush.doTheJob(conf, methodName, block_size, (short)3);
    if (trueVerification)
      assertTrue("Some of expected conditions weren't detected", hft.isSuccess());
  }

};