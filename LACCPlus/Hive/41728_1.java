//,temp,TestReplicationScenarios.java,4774,4782,temp,TestReplicationScenarios.java,4709,4720
//,3
public class xxx {
  private static void run(String cmd, IDriver myDriver) throws RuntimeException {
    try {
    run(cmd,false, myDriver); // default arg-less run simply runs, and does not care about failure
    } catch (AssertionError ae){
      // Hive code has AssertionErrors in some cases - we want to record what happens
      LOG.warn("AssertionError:",ae);
      throw new RuntimeException(ae);
    }
  }

};