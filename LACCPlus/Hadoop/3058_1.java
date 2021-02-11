//,temp,TestPipelinesFailover.java,518,529,temp,FsDatasetAsyncDiskService.java,236,242
//,3
public class xxx {
  private int failover(MiniDFSCluster cluster, TestScenario scenario, int activeIndex)
      throws IOException {
    // get index of the next node that should be active, ensuring its not the same as the currently
    // active node
    int nextActive = failoverRandom.nextInt(NN_COUNT);
    if (nextActive == activeIndex) {
      nextActive = (nextActive + 1) % NN_COUNT;
    }
    LOG.info("Failing over to a standby NN:" + nextActive + " from NN " + activeIndex);
    scenario.run(cluster, activeIndex, nextActive);
    return nextActive;
  }

};