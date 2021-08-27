//,temp,CoreCliDriver.java,157,198,temp,CorePerfCliDriver.java,93,122
//,3
public class xxx {
  @Override
  public void runTest(String testName, String fname, String fpath) {
    Stopwatch sw = Stopwatch.createStarted();
    boolean skipped = false;
    boolean failed = false;
    try {
      LOG.info("Begin query: " + fname);
      System.err.println("Begin query: " + fname);

      qt.addFile(fpath);
      qt.cliInit(new File(fpath));

      try {
        qt.executeClient(fname);
      } catch (CommandProcessorException e) {
        failed = true;
        qt.failedQuery(e.getCause(), e.getResponseCode(), fname, QTestUtil.DEBUG_HINT);
      }

      setupAdditionalPartialMasks();
      QTestProcessExecResult result = qt.checkCliDriverResults(fname);
      resetAdditionalPartialMasks();
      if (result.getReturnCode() != 0) {
        failed = true;
        String message = Strings.isNullOrEmpty(result.getCapturedOutput()) ? QTestUtil.DEBUG_HINT
            : "\r\n" + result.getCapturedOutput();
        qt.failedDiff(result.getReturnCode(), fname, message);
      }
    } catch (AssumptionViolatedException e) {
      skipped = true;
      throw e;
    } catch (Exception e) {
      failed = true;
      qt.failedWithException(e, fname, QTestUtil.DEBUG_HINT);
    } finally {
      String message = "Done query " + fname + ". succeeded=" + !failed + ", skipped=" + skipped +
          ". ElapsedTime(ms)=" + sw.stop().elapsed(TimeUnit.MILLISECONDS);
      LOG.info(message);
      System.err.println(message);
    }
    assertTrue("Test passed", true);
  }

};