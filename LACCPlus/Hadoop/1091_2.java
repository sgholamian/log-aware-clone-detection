//,temp,TestBalancer.java,680,732,temp,Balancer.java,581,627
//,3
public class xxx {
  static int run(Collection<URI> namenodes, final Parameters p,
      Configuration conf) throws IOException, InterruptedException {
    final long sleeptime =
        conf.getLong(DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY,
            DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_DEFAULT) * 2000 +
        conf.getLong(DFSConfigKeys.DFS_NAMENODE_REPLICATION_INTERVAL_KEY,
            DFSConfigKeys.DFS_NAMENODE_REPLICATION_INTERVAL_DEFAULT) * 1000;
    LOG.info("namenodes  = " + namenodes);
    LOG.info("parameters = " + p);
    
    System.out.println("Time Stamp               Iteration#  Bytes Already Moved  Bytes Left To Move  Bytes Being Moved");
    
    List<NameNodeConnector> connectors = Collections.emptyList();
    try {
      connectors = NameNodeConnector.newNameNodeConnectors(namenodes, 
            Balancer.class.getSimpleName(), BALANCER_ID_PATH, conf, p.maxIdleIteration);
    
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