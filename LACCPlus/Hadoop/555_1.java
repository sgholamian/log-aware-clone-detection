//,temp,TestChildQueueOrder.java,126,158,temp,TestParentQueue.java,138,169
//,2
public class xxx {
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
          when(queue)
              .assignContainers(eq(clusterResource), eq(node),
                  any(ResourceLimits.class), any(SchedulingMode.class));

          // Mock the node's resource availability
          Resource available = node.getAvailableResource();
          doReturn(Resources.subtractFrom(available, allocatedResource)).
          when(node).getAvailableResource();
        }

        return new CSAssignment(allocatedResource, type);
      }

};