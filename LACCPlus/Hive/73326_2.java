//,temp,HiveMetaStore.java,771,793,temp,HiveStringUtils.java,752,779
//,3
public class xxx {
  public static void startupShutdownMessage(Class<?> clazz, String[] args,
                                     final org.slf4j.Logger LOG) {
    final String hostname = getHostname();
    final String classname = clazz.getSimpleName();
    LOG.info(
        toStartupShutdownString("STARTUP_MSG: ", new String[] {
            "Starting " + classname,
            "  host = " + hostname,
            "  args = " + Arrays.asList(args),
            "  version = " + HiveVersionInfo.getVersion(),
            "  classpath = " + System.getProperty("java.class.path"),
            "  build = " + HiveVersionInfo.getUrl() + " -r "
                         + HiveVersionInfo.getRevision()
                         + "; compiled by '" + HiveVersionInfo.getUser()
                         + "' on " + HiveVersionInfo.getDate()}
        )
      );

    ShutdownHookManager.addShutdownHook(
      new Runnable() {
        @Override
        public void run() {
          LOG.info(toStartupShutdownString("SHUTDOWN_MSG: ", new String[]{
            "Shutting down " + classname + " at " + hostname}));
        }
      }, SHUTDOWN_HOOK_PRIORITY);

  }

};