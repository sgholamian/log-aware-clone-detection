//,temp,LlapTaskSchedulerService.java,1571,1595,temp,LlapTaskSchedulerService.java,886,902
//,3
public class xxx {
    @Override
    public void onRemove(LlapServiceInstance serviceInstance, int ephSeqVersion) {
      NodeReport nodeReport = constructNodeReport(serviceInstance, false);
      LOG.info("Sending out nodeReport for onRemove: {}", nodeReport);
      getContext().nodesUpdated(Collections.singletonList(nodeReport));
      instanceToNodeMap.remove(serviceInstance.getWorkerIdentity());
      LOG.info("Removed node with identity: {} due to RegistryNotification. currentActiveInstances={}",
          serviceInstance.getWorkerIdentity(), activeInstances.size());
      if (metrics != null) {
        metrics.setClusterNodeCount(activeInstances.size());
      }
      // if there are no more nodes. Signal timeout monitor to start timer
      if (activeInstances.size() == 0) {
        LOG.info("No node found. Signalling scheduler timeout monitor thread to start timer.");
        startTimeoutMonitor();
      }
    }

};