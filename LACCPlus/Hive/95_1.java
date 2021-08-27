//,temp,TestAutoPurgeTables.java,310,314,temp,TestAutoPurgeTables.java,296,300
//,3
public class xxx {
  @Test(expected = SQLException.class)
  public void testTruncateExternalNoAutoPurge() throws Exception {
    LOG.info("Running " + name.getMethodName());
    testUtil(String.valueOf(false), true, false, true, name.getMethodName());
  }

};