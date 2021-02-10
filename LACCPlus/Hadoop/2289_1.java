//,temp,StatusReportChecker.java,51,70,temp,CgroupsLCEResourcesHandler.java,301,325
//,3
public class xxx {
  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(interval);
      } catch (final InterruptedException e) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("StatusUpdater thread exiting " + "since it got interrupted");
        }
        break;
      }
      try {
        NativeRuntime.reportStatus(reporter);
      } catch (final IOException e) {
        LOG.warn("Update native status got exception", e);
        reporter.setStatus(e.toString());
        break;
      }
    }
  }

};