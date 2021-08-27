//,temp,TestAutoPurgeTables.java,341,345,temp,TestAutoPurgeTables.java,272,276
//,3
public class xxx {
  @Test(expected = SQLException.class)
  public void testTruncatePartitionedExternalAutoPurge() throws Exception {
    LOG.info("Running " + name.getMethodName());
    testUtil(String.valueOf(true), true, true, true, name.getMethodName());
  }

};