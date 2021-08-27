//,temp,TestAutoPurgeTables.java,316,320,temp,TestAutoPurgeTables.java,200,204
//,3
public class xxx {
  @Test
  public void testTruncatePartitionedNoAutoPurge() throws Exception {
    LOG.info("Running " + name.getMethodName());
    testUtil(String.valueOf(false), false, true, true, name.getMethodName());
  }

};