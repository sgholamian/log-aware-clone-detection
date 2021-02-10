//,temp,TestStandbyCheckpoints.java,74,111,temp,TestEditLogAutoroll.java,55,94
//,3
public class xxx {
  @SuppressWarnings("rawtypes")
  @Before
  public void setupCluster() throws Exception {
    Configuration conf = setupCommonConfig();

    // Dial down the retention of extra edits and checkpoints. This is to
    // help catch regressions of HDFS-4238 (SBN should not purge shared edits)
    conf.setInt(DFSConfigKeys.DFS_NAMENODE_NUM_CHECKPOINTS_RETAINED_KEY, 1);
    conf.setInt(DFSConfigKeys.DFS_NAMENODE_NUM_EXTRA_EDITS_RETAINED_KEY, 0);

    int retryCount = 0;
    while (true) {
      try {
        int basePort = 10060 + random.nextInt(100) * 2;
        MiniDFSNNTopology topology = new MiniDFSNNTopology()
            .addNameservice(new MiniDFSNNTopology.NSConf("ns1")
                .addNN(new MiniDFSNNTopology.NNConf("nn1").setHttpPort(basePort))
                .addNN(new MiniDFSNNTopology.NNConf("nn2").setHttpPort(basePort + 1)));

        cluster = new MiniDFSCluster.Builder(conf)
            .nnTopology(topology)
            .numDataNodes(1)
            .build();
        cluster.waitActive();

        nn0 = cluster.getNameNode(0);
        nn1 = cluster.getNameNode(1);
        fs = HATestUtil.configureFailoverFs(cluster, conf);

        cluster.transitionToActive(0);
        ++retryCount;
        break;
      } catch (BindException e) {
        LOG.info("Set up MiniDFSCluster failed due to port conflicts, retry "
            + retryCount + " times");
      }
    }
  }

};