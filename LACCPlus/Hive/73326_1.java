//,temp,HiveMetaStore.java,771,793,temp,HiveStringUtils.java,752,779
//,3
public class xxx {
  private static void startupShutdownMessage(Class<?> clazz, String[] args,
                                             final org.slf4j.Logger LOG) {
    final String hostname = getHostname();
    final String classname = clazz.getSimpleName();
    LOG.info(
        toStartupShutdownString("STARTUP_MSG: ", new String[] {
            "Starting " + classname,
            "  host = " + hostname,
            "  args = " + Arrays.asList(args),
            "  version = " + MetastoreVersionInfo.getVersion(),
            "  classpath = " + System.getProperty("java.class.path"),
            "  build = " + MetastoreVersionInfo.getUrl() + " -r "
                + MetastoreVersionInfo.getRevision()
                + "; compiled by '" + MetastoreVersionInfo.getUser()
                + "' on " + MetastoreVersionInfo.getDate()}
        )
    );

    shutdownHookMgr.addShutdownHook(
        () -> LOG.info(toStartupShutdownString("SHUTDOWN_MSG: ", new String[]{
            "Shutting down " + classname + " at " + hostname})), 0);

  }

};