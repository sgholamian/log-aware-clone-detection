//,temp,TestJobHistoryParsing.java,141,149,temp,TestJobHistoryParsing.java,131,139
//,3
public class xxx {
  @Test(timeout = 50000)
  public void testHistoryParsingWithParseErrors() throws Exception {
    LOG.info("STARTING testHistoryParsingWithParseErrors()");
    try {
      checkHistoryParsing(3, 0, 2);
    } finally {
      LOG.info("FINISHED testHistoryParsingWithParseErrors()");
    }
  }

};