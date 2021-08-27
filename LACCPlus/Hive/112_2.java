//,temp,TestAutoPurgeTables.java,354,358,temp,TestAutoPurgeTables.java,284,288
//,3
public class xxx {
  @Test
  public void testPartitionedNoAutoPurge() throws Exception {
    LOG.info("Running " + name.getMethodName());
    testUtil("false", false, true, false, name.getMethodName());
  }

};