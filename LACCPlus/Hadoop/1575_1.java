//,temp,Mover.java,546,586,temp,Balancer.java,581,627
//,3
public class xxx {
  static int run(Map<URI, List<Path>> namenodes, Configuration conf)
      throws IOException, InterruptedException {
    final long sleeptime =
        conf.getLong(DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY,
            DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_DEFAULT) * 2000 +
        conf.getLong(DFSConfigKeys.DFS_NAMENODE_REPLICATION_INTERVAL_KEY,
            DFSConfigKeys.DFS_NAMENODE_REPLICATION_INTERVAL_DEFAULT) * 1000;
    AtomicInteger retryCount = new AtomicInteger(0);
    LOG.info("namenodes = " + namenodes);
    
    List<NameNodeConnector> connectors = Collections.emptyList();
    try {
      connectors = NameNodeConnector.newNameNodeConnectors(namenodes,
          Mover.class.getSimpleName(), MOVER_ID_PATH, conf,
          NameNodeConnector.DEFAULT_MAX_IDLE_ITERATIONS);

      while (connectors.size() > 0) {
        Collections.shuffle(connectors);
        Iterator<NameNodeConnector> iter = connectors.iterator();
        while (iter.hasNext()) {
          NameNodeConnector nnc = iter.next();
          final Mover m = new Mover(nnc, conf, retryCount);
          final ExitStatus r = m.run();

          if (r == ExitStatus.SUCCESS) {
            IOUtils.cleanup(LOG, nnc);
            iter.remove();
          } else if (r != ExitStatus.IN_PROGRESS) {
            // must be an error statue, return
            return r.getExitCode();
          }
        }
        Thread.sleep(sleeptime);
      }
      return ExitStatus.SUCCESS.getExitCode();
    } finally {
      for (NameNodeConnector nnc : connectors) {
        IOUtils.cleanup(LOG, nnc);
      }
    }
  }

};