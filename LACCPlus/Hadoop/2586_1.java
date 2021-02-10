//,temp,ReservationACLsTestBase.java,450,456,temp,TestIPC.java,563,569
//,3
public class xxx {
  private void handleAdministerException(Exception e, String user, String
          queue, String operation) {
    LOG.info("Got exception while killing app as the enemy", e);
    Assert.assertTrue(e.getMessage().contains("User " + user
            + " cannot perform operation " + operation + " on queue "
            + queue));
  }

};