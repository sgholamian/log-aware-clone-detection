//,temp,TestJobHistoryParsing.java,141,149,temp,TestJobHistoryParsing.java,131,139
//,3
public class xxx {
  @Test(timeout = 300000)
  public void testHistoryParsing() throws Exception {
    LOG.info("STARTING testHistoryParsing()");
    try {
      checkHistoryParsing(2, 1, 2);
    } finally {
      LOG.info("FINISHED testHistoryParsing()");
    }
  }

};