//,temp,RatisManagerImpl.java,76,100,temp,StandaloneManagerImpl.java,76,97
//,3
public class xxx {
  public Pipeline allocatePipeline(ReplicationFactor factor) {
    List<DatanodeDetails> newNodesList = new LinkedList<>();
    List<DatanodeDetails> datanodes = nodeManager.getNodes(NodeState.HEALTHY);
    int count = getReplicationCount(factor);
    for (DatanodeDetails datanode : datanodes) {
      Preconditions.checkNotNull(datanode);
      if (!standAloneMembers.contains(datanode)) {
        newNodesList.add(datanode);
        if (newNodesList.size() == count) {
          // once a datanode has been added to a pipeline, exclude it from
          // further allocations
          standAloneMembers.addAll(newNodesList);
          LOG.info("Allocating a new standalone pipeline of size: {}", count);
          String pipelineName =
              "SA-" + UUID.randomUUID().toString().substring(3);
          return PipelineSelector.newPipelineFromNodes(newNodesList,
              ReplicationType.STAND_ALONE, ReplicationFactor.ONE, pipelineName);
        }
      }
    }
    return null;
  }

};