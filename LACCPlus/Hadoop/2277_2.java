//,temp,S3AUtils.java,1291,1307,temp,AbstractServiceLauncherTestBase.java,152,166
//,3
public class xxx {
  protected void assertLaunchOutcome(int expected,
      String text,
      String... args) {
    try {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Launching service with expected outcome {}", expected);
        for (String arg : args) {
          LOG.debug(arg);
        }
      }
      ServiceLauncher.serviceMain(args);
    } catch (ServiceLaunchException e) {
      assertExceptionDetails(expected, text, e);
    }
  }

};