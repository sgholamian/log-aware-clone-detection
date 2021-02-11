//,temp,RatisManagerImpl.java,76,100,temp,StandaloneManagerImpl.java,76,97
//,3
public class xxx {
  public Pipeline allocatePipeline(ReplicationFactor factor) {
    List<DatanodeDetails> newNodesList = new LinkedList<>();
    List<DatanodeDetails> datanodes = nodeManager.getNodes(NodeState.HEALTHY);
    int count = getReplicationCount(factor);
    //TODO: Add Raft State to the Nodes, so we can query and skip nodes from
    // data from datanode instead of maintaining a set.
    for (DatanodeDetails datanode : datanodes) {
      Preconditions.checkNotNull(datanode);
      if (!ratisMembers.contains(datanode)) {
        newNodesList.add(datanode);
        if (newNodesList.size() == count) {
          // once a datanode has been added to a pipeline, exclude it from
          // further allocations
          ratisMembers.addAll(newNodesList);
          LOG.info("Allocating a new ratis pipeline of size: {}", count);
          // Start all pipeline names with "Ratis", easy to grep the logs.
          String pipelineName = PREFIX +
              UUID.randomUUID().toString().substring(PREFIX.length());
          return PipelineSelector.newPipelineFromNodes(newNodesList,
              ReplicationType.RATIS, factor, pipelineName);
        }
      }
    }
    return null;
  }

};