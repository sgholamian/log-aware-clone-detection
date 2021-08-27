//,temp,LlapTaskSchedulerService.java,1571,1595,temp,LlapTaskSchedulerService.java,886,902
//,3
public class xxx {
  private void addNode(NodeInfo node, LlapServiceInstance serviceInstance) {
    // we have just added a new node. Signal timeout monitor to reset timer
    if (activeInstances.size() != 0 && timeoutFutureRef.get() != null) {
      LOG.info("New node added. Signalling scheduler timeout monitor thread to stop timer.");
      stopTimeoutMonitor();
    }

    NodeReport nodeReport = constructNodeReport(serviceInstance, true);
    getContext().nodesUpdated(Collections.singletonList(nodeReport));

    // When the same node goes away and comes back... the old entry will be lost - which means
    // we don't know how many fragments we have actually scheduled on this node.

    // Replacing it is the right thing to do though, since we expect the AM to kill all the fragments running on the node, via timeouts.
    // De-allocate messages coming in from the old node are sent to the NodeInfo instance for the old node.

    instanceToNodeMap.put(node.getNodeIdentity(), node);
    if (metrics != null) {
      metrics.setClusterNodeCount(activeInstances.size());
    }
    // Trigger scheduling since a new node became available.
    LOG.info("Adding new node: {}. TotalNodeCount={}. activeInstances.size={}",
        node, instanceToNodeMap.size(), activeInstances.size());
    trySchedulingPendingTasks();
  }

};