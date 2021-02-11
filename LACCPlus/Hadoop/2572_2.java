//,temp,SwiftNativeFileSystem.java,156,162,temp,CGroupsResourceCalculator.java,146,152
//,3
public class xxx {
  @Override
  public float getCpuUsagePercent() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Process " + pid + " jiffies:" + processTotalJiffies);
    }
    return cpuTimeTracker.getCpuTrackerUsagePercent();
  }

};