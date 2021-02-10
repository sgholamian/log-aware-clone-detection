//,temp,MiniMRCluster.java,106,114,temp,MiniMRCluster.java,96,104
//,3
public class xxx {
  public JobConf createJobConf() {
    JobConf jobConf = null;
    try {
      jobConf = new JobConf(mrClientCluster.getConfig());
    } catch (IOException e) {
      LOG.error(e);
    }
    return jobConf;
  }

};