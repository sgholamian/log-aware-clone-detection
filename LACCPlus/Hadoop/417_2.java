//,temp,TestStandbyCheckpoints.java,74,111,temp,TestEditLogAutoroll.java,55,94
//,3
public class xxx {
  @Before
  public void setUp() throws Exception {
    conf = new Configuration();
    // Stall the standby checkpointer in two ways
    conf.setLong(DFS_NAMENODE_CHECKPOINT_PERIOD_KEY, Long.MAX_VALUE);
    conf.setLong(DFS_NAMENODE_CHECKPOINT_TXNS_KEY, 20);
    // Make it autoroll after 10 edits
    conf.setFloat(DFS_NAMENODE_EDIT_LOG_AUTOROLL_MULTIPLIER_THRESHOLD, 0.5f);
    conf.setInt(DFS_NAMENODE_EDIT_LOG_AUTOROLL_CHECK_INTERVAL_MS, 100);

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
            .numDataNodes(0)
            .build();
        cluster.waitActive();

        nn0 = cluster.getNameNode(0);
        fs = HATestUtil.configureFailoverFs(cluster, conf);

        cluster.transitionToActive(0);

        fs = cluster.getFileSystem(0);
        editLog = nn0.getNamesystem().getEditLog();
        ++retryCount;
        break;
      } catch (BindException e) {
        LOG.info("Set up MiniDFSCluster failed due to port conflicts, retry "
            + retryCount + " times");
      }
    }
  }

};