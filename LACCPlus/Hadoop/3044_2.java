//,temp,AbstractLauncher.java,191,201,temp,NativeAzureFileSystemHelper.java,121,130
//,3
public class xxx {
  public static void logAllLiveStackTraces() {

    for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
      LOG.debug("Thread " + entry.getKey().getName());
      StackTraceElement[] trace = entry.getValue();
      for (int j = 0; j < trace.length; j++) {
        LOG.debug("\tat " + trace[j]);
      }
    }
  }

};