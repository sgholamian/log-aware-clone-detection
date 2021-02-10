//,temp,RetryInvocationHandler.java,391,415,temp,SchedulerMetrics.java,660,673
//,3
public class xxx {
  public void addAMRuntime(ApplicationId appId, long traceStartTimeMS,
      long traceEndTimeMS, long simulateStartTimeMS, long simulateEndTimeMS) {
    try {
      // write job runtime information
      StringBuilder sb = new StringBuilder();
      sb.append(appId).append(",").append(traceStartTimeMS).append(",")
          .append(traceEndTimeMS).append(",").append(simulateStartTimeMS)
          .append(",").append(simulateEndTimeMS);
      jobRuntimeLogBW.write(sb.toString() + EOL);
      jobRuntimeLogBW.flush();
    } catch (IOException e) {
      LOG.info(e.getMessage());
    }
  }

};