//,temp,TestStreamingDynamicPartitioning.java,825,835,temp,TestStreaming.java,3199,3210
//,2
public class xxx {
  private static boolean runDDL(IDriver driver, String sql) {
    LOG.debug(sql);
    System.out.println(sql);
    //LOG.debug("Running Hive Query: "+ sql);
    try {
      driver.run(sql);
      return true;
    } catch (CommandProcessorException e) {
      LOG.error("Statement: " + sql + " failed: " + e);
      return false;
    }
  }

};