//,temp,TestAutoPurgeTables.java,310,314,temp,TestAutoPurgeTables.java,296,300
//,3
public class xxx {
  @Test
  public void testPartitionedExternalNoAutoPurge() throws Exception {
    LOG.info("Running " + name.getMethodName());
    testUtil("false", true, true, false, name.getMethodName());
  }

};