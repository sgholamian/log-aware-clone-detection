//,temp,NNThroughputBenchmark.java,1371,1390,temp,NNThroughputBenchmark.java,1221,1236
//,3
public class xxx {
    @Override
    void printResults() {
      String blockDistribution = "";
      String delim = "(";
      for(int idx=0; idx < blockReportObject.getNumDatanodes(); idx++) {
        blockDistribution += delim + blockReportObject.datanodes[idx].nrBlocks;
        delim = ", ";
      }
      blockDistribution += ")";
      LOG.info("--- " + getOpName() + " inputs ---");
      LOG.info("numOpsRequired = " + numOpsRequired);
      LOG.info("datanodes = " + numDatanodes + " " + blockDistribution);
      LOG.info("decommissioned datanodes = " + nodesToDecommission);
      LOG.info("datanode replication limit = " + nodeReplicationLimit);
      LOG.info("total blocks = " + totalBlocks);
      printStats();
      LOG.info("decommissioned blocks = " + numDecommissionedBlocks);
      LOG.info("pending replications = " + numPendingBlocks);
      LOG.info("replications per sec: " + getBlocksPerSecond());
    }

};