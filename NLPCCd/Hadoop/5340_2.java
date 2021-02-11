//,temp,sample_354.java,2,16,temp,sample_398.java,2,16
//,3
public class xxx {
public void dummy_method(){
root.assignContainers(clusterResource, node_0, new ResourceLimits(clusterResource), SchedulingMode.RESPECT_PARTITION_EXCLUSIVITY);
allocationOrder.verify(c).assignContainers(eq(clusterResource), any(PlacementSet.class), anyResourceLimits(), any(SchedulingMode.class));
applyAllocationToQueue(clusterResource, 2*GB, root);
root.assignContainers(clusterResource, node_0, new ResourceLimits(clusterResource), SchedulingMode.RESPECT_PARTITION_EXCLUSIVITY);
allocationOrder.verify(b).assignContainers(eq(clusterResource), any(PlacementSet.class), anyResourceLimits(), any(SchedulingMode.class));
applyAllocationToQueue(clusterResource, 2*GB, b);
verifyQueueMetrics(a, 1*GB, clusterResource);
verifyQueueMetrics(b, 6*GB, clusterResource);
verifyQueueMetrics(c, 3*GB, clusterResource);
reset(a); reset(b); reset(c);


log.info("here");
}

};