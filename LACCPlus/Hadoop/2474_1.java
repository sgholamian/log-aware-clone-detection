//,temp,TestChildQueueOrder.java,122,167,temp,TestParentQueue.java,178,220
//,3
public class xxx {
  @SuppressWarnings("unchecked")
  private void stubQueueAllocation(final CSQueue queue,
      final Resource clusterResource, final FiCaSchedulerNode node,
      final int allocation, final NodeType type) {

    // Simulate the queue allocation
    doAnswer(new Answer<CSAssignment>() {
      @Override
      public CSAssignment answer(InvocationOnMock invocation) throws Throwable {
        try {
          throw new Exception();
        } catch (Exception e) {
          LOG.info("FOOBAR q.assignContainers q=" + queue.getQueueName() + 
              " alloc=" + allocation + " node=" + node.getNodeName());
        }
        final Resource allocatedResource = Resources.createResource(allocation);
        if (queue instanceof ParentQueue) {
          ((ParentQueue)queue).allocateResource(clusterResource, 
              allocatedResource, RMNodeLabelsManager.NO_LABEL);
        } else {
          FiCaSchedulerApp app1 = getMockApplication(0, "");
          ((LeafQueue)queue).allocateResource(clusterResource, app1, 
              allocatedResource, null, null);
        }

        // Next call - nothing
        if (allocation > 0) {
          doReturn(new CSAssignment(Resources.none(), type)).
              when(queue).assignContainers(eq(clusterResource),
              any(CandidateNodeSet.class), any(ResourceLimits.class),
              any(SchedulingMode.class));

          // Mock the node's resource availability
          Resource available = node.getUnallocatedResource();
          doReturn(Resources.subtractFrom(available, allocatedResource)).
          when(node).getUnallocatedResource();
        }

        return new CSAssignment(allocatedResource, type);
      }
    }).when(queue).assignContainers(eq(clusterResource),
        any(CandidateNodeSet.class), any(ResourceLimits.class),
        any(SchedulingMode.class));
    doNothing().when(node).releaseContainer(any(ContainerId.class),
        anyBoolean());
  }

};