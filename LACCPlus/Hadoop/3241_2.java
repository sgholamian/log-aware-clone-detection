//,temp,TestBalancer.java,974,1029,temp,Balancer.java,674,731
//,3
public class xxx {
  static int run(Collection<URI> namenodes, final BalancerParameters p,
      Configuration conf) throws IOException, InterruptedException {
    final long sleeptime =
        conf.getTimeDuration(DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY,
            DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_DEFAULT,
            TimeUnit.SECONDS) * 2000 +
        conf.getTimeDuration(
            DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_INTERVAL_SECONDS_KEY,
            DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_INTERVAL_SECONDS_DEFAULT,
            TimeUnit.SECONDS) * 1000;
    LOG.info("namenodes  = " + namenodes);
    LOG.info("parameters = " + p);
    LOG.info("included nodes = " + p.getIncludedNodes());
    LOG.info("excluded nodes = " + p.getExcludedNodes());
    LOG.info("source nodes = " + p.getSourceNodes());
    checkKeytabAndInit(conf);
    System.out.println("Time Stamp               Iteration#  Bytes Already Moved  Bytes Left To Move  Bytes Being Moved");
    
    List<NameNodeConnector> connectors = Collections.emptyList();
    try {
      connectors = NameNodeConnector.newNameNodeConnectors(namenodes, 
              Balancer.class.getSimpleName(), BALANCER_ID_PATH, conf,
              p.getMaxIdleIteration());

      boolean done = false;
      for(int iteration = 0; !done; iteration++) {
        done = true;
        Collections.shuffle(connectors);
        for(NameNodeConnector nnc : connectors) {
          if (p.getBlockPools().size() == 0
              || p.getBlockPools().contains(nnc.getBlockpoolID())) {
            final Balancer b = new Balancer(nnc, p, conf);
            final Result r = b.runOneIteration();
            r.print(iteration, System.out);

            // clean all lists
            b.resetData(conf);
            if (r.exitStatus == ExitStatus.IN_PROGRESS) {
              done = false;
            } else if (r.exitStatus != ExitStatus.SUCCESS) {
              // must be an error statue, return.
              return r.exitStatus.getExitCode();
            }
          } else {
            LOG.info("Skipping blockpool " + nnc.getBlockpoolID());
          }
        }
        if (!done) {
          Thread.sleep(sleeptime);
        }
      }
    } finally {
      for(NameNodeConnector nnc : connectors) {
        IOUtils.cleanup(LOG, nnc);
      }
    }
    return ExitStatus.SUCCESS.getExitCode();
  }

};