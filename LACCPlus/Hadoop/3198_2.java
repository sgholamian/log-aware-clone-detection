//,temp,NMTimelinePublisher.java,273,313,temp,NMTimelinePublisher.java,156,209
//,3
public class xxx {
  public void reportContainerResourceUsage(Container container, Long pmemUsage,
      Float cpuUsagePercentPerCore) {
    if (pmemUsage != ResourceCalculatorProcessTree.UNAVAILABLE ||
        cpuUsagePercentPerCore != ResourceCalculatorProcessTree.UNAVAILABLE) {
      ContainerEntity entity =
          createContainerEntity(container.getContainerId());
      long currentTimeMillis = System.currentTimeMillis();
      if (pmemUsage != ResourceCalculatorProcessTree.UNAVAILABLE) {
        TimelineMetric memoryMetric = new TimelineMetric();
        memoryMetric.setId(ContainerMetric.MEMORY.toString());
        memoryMetric.setRealtimeAggregationOp(TimelineMetricOperation.SUM);
        memoryMetric.addValue(currentTimeMillis, pmemUsage);
        entity.addMetric(memoryMetric);
      }
      if (cpuUsagePercentPerCore != ResourceCalculatorProcessTree.UNAVAILABLE) {
        TimelineMetric cpuMetric = new TimelineMetric();
        cpuMetric.setId(ContainerMetric.CPU.toString());
        // TODO: support average
        cpuMetric.setRealtimeAggregationOp(TimelineMetricOperation.SUM);
        cpuMetric.addValue(currentTimeMillis,
            Math.round(cpuUsagePercentPerCore));
        entity.addMetric(cpuMetric);
      }
      entity.setIdPrefix(TimelineServiceHelper.
          invertLong(container.getContainerStartTime()));
      ApplicationId appId = container.getContainerId().getApplicationAttemptId()
          .getApplicationId();
      try {
        // no need to put it as part of publisher as timeline client already has
        // Queuing concept
        TimelineV2Client timelineClient = getTimelineClient(appId);
        if (timelineClient != null) {
          timelineClient.putEntitiesAsync(entity);
        } else {
          LOG.error("Seems like client has been removed before the container"
              + " metric could be published for " + container.getContainerId());
        }
      } catch (IOException e) {
        LOG.error("Failed to publish Container metrics for container "
            + container.getContainerId());
        if (LOG.isDebugEnabled()) {
          LOG.debug("Failed to publish Container metrics for container "
              + container.getContainerId(), e);
        }
      } catch (YarnException e) {
        LOG.error("Failed to publish Container metrics for container "
            + container.getContainerId(), e.getMessage());
        if (LOG.isDebugEnabled()) {
          LOG.debug("Failed to publish Container metrics for container "
              + container.getContainerId(), e);
        }
      }
    }
  }

};