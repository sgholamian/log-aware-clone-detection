//,temp,MiniMRCluster.java,222,230,temp,MiniMRCluster.java,96,104
//,2
public class xxx {
  public JobConf getJobTrackerConf() {
    JobConf jobConf = null;
    try {
      jobConf = new JobConf(mrClientCluster.getConfig());
    } catch (IOException e) {
      LOG.error(e);
    }
    return jobConf;
  }

};