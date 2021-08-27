//,temp,CoreCliDriver.java,157,198,temp,CorePerfCliDriver.java,93,122
//,3
public class xxx {
  @Override
  public void runTest(String name, String fname, String fpath) {
    long startTime = System.currentTimeMillis();
    try {
      LOG.info("Begin query: " + fname);

      qt.addFile(fpath);
      qt.cliInit(new File(fpath));

      try {
        qt.executeClient(fname);
      } catch (CommandProcessorException e) {
        qt.failedQuery(e.getCause(), e.getResponseCode(), fname, QTestUtil.DEBUG_HINT);
      }

      QTestProcessExecResult result = qt.checkCliDriverResults(fname);
      if (result.getReturnCode() != 0) {
        String message = Strings.isNullOrEmpty(result.getCapturedOutput()) ? QTestUtil.DEBUG_HINT :
            "\r\n" + result.getCapturedOutput();
        qt.failedDiff(result.getReturnCode(), fname, message);
      }
    } catch (AssumptionViolatedException e) {
      throw e;
    } catch (Exception e) {
      qt.failedWithException(e, fname, QTestUtil.DEBUG_HINT);
    }

    long elapsedTime = System.currentTimeMillis() - startTime;
    LOG.info("Done query: " + fname + " elapsedTime=" + elapsedTime / 1000 + "s");
  }

};