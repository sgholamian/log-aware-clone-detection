//,temp,TestAutoPurgeTables.java,348,352,temp,TestAutoPurgeTables.java,328,332
//,3
public class xxx {
  @Test(expected = SQLException.class)
  public void testTruncateExternalAutoPurge() throws Exception {
    LOG.info("Running " + name.getMethodName());
    testUtil(String.valueOf(true), true, false, true, name.getMethodName());
  }

};