//,temp,ReservationACLsTestBase.java,450,456,temp,TestIPC.java,563,569
//,3
public class xxx {
  private static void assertExceptionContains(
      Throwable t, String substring) {
    String msg = StringUtils.stringifyException(t);
    assertTrue("Exception should contain substring '" + substring + "':\n" +
        msg, msg.contains(substring));
    LOG.info("Got expected exception", t);
  }

};