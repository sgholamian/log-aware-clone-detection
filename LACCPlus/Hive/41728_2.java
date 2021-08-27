//,temp,TestReplicationScenarios.java,4774,4782,temp,TestReplicationScenarios.java,4709,4720
//,3
public class xxx {
  private void verifyFail(String cmd, IDriver myDriver) throws RuntimeException {
    boolean success = false;
    try {
      success = run(cmd, false, myDriver);
    } catch (AssertionError ae){
      LOG.warn("AssertionError:",ae);
      throw new RuntimeException(ae);
    } catch (Exception e) {
      success = false;
    }
    assertFalse(success);
  }

};