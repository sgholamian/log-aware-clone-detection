//,temp,TestAutoPurgeTables.java,303,307,temp,TestAutoPurgeTables.java,224,228
//,3
public class xxx {
  @Test(expected = SQLException.class)
  public void testTruncatePartitionedExternalNoAutoPurge() throws Exception {
    LOG.info("Running " + name.getMethodName());
    testUtil(String.valueOf(false), true, true, true, name.getMethodName());
  }

};