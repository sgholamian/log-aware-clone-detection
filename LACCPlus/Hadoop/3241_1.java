//,temp,TestBalancer.java,974,1029,temp,Balancer.java,674,731
//,3
public class xxx {
  private static int runBalancer(Collection<URI> namenodes,
      final BalancerParameters p,
      Configuration conf) throws IOException, InterruptedException {
    final long sleeptime = conf.getLong(
        DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY,
        DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_DEFAULT) * 2000
        + conf.getLong(
            DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_INTERVAL_SECONDS_KEY,
            DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_INTERVAL_SECONDS_DEFAULT)
            * 1000;
    LOG.info("namenodes  = " + namenodes);
    LOG.info("parameters = " + p);
    LOG.info("Print stack trace", new Throwable());

    System.out.println("Time Stamp               Iteration#  Bytes Already Moved  Bytes Left To Move  Bytes Being Moved");

    List<NameNodeConnector> connectors = Collections.emptyList();
    try {
      connectors = NameNodeConnector.newNameNodeConnectors(namenodes,
          Balancer.class.getSimpleName(), Balancer.BALANCER_ID_PATH, conf,
              BalancerParameters.DEFAULT.getMaxIdleIteration());

      boolean done = false;
      for(int iteration = 0; !done; iteration++) {
        done = true;
        Collections.shuffle(connectors);
        for(NameNodeConnector nnc : connectors) {
          final Balancer b = new Balancer(nnc, p, conf);
          final Result r = b.runOneIteration();
          r.print(iteration, System.out);

          // clean all lists
          b.resetData(conf);
          if (r.exitStatus == ExitStatus.IN_PROGRESS) {
            done = false;
          } else if (r.exitStatus != ExitStatus.SUCCESS) {
            //must be an error statue, return.
            return r.exitStatus.getExitCode();
          } else {
            if (iteration > 0) {
              assertTrue(r.bytesAlreadyMoved > 0);
            }
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