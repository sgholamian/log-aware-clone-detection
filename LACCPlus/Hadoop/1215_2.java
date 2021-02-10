//,temp,MiniMRCluster.java,222,230,temp,MiniMRCluster.java,106,114
//,3
public class xxx {
  public JobConf createJobConf(JobConf conf) {
    JobConf jobConf = null;
    try {
      jobConf = new JobConf(mrClientCluster.getConfig());
    } catch (IOException e) {
      LOG.error(e);
    }
    return jobConf;
  }

};