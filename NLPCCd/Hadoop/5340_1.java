//,temp,sample_354.java,2,16,temp,sample_398.java,2,16
//,3
public class xxx {
public void dummy_method(){
stubQueueAllocation(c, clusterResource, node_0, 0*GB);
stubQueueAllocation(d, clusterResource, node_0, 1*GB);
root.assignContainers(clusterResource, node_0, new ResourceLimits( clusterResource), SchedulingMode.RESPECT_PARTITION_EXCLUSIVITY);
InOrder allocationOrder = inOrder(d,b);
allocationOrder.verify(d).assignContainers(eq(clusterResource), any(PlacementSet.class), any(ResourceLimits.class), any(SchedulingMode.class));
allocationOrder.verify(b).assignContainers(eq(clusterResource), any(PlacementSet.class), any(ResourceLimits.class), any(SchedulingMode.class));
verifyQueueMetrics(a, 3*GB, clusterResource);
verifyQueueMetrics(b, 2*GB, clusterResource);
verifyQueueMetrics(c, 3*GB, clusterResource);
verifyQueueMetrics(d, 2*GB, clusterResource);


log.info("status child queues");
}

};