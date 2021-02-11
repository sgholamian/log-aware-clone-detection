//,temp,TestResourceManager.java,86,179,temp,TestCapacityScheduler.java,250,368
//,3
public class xxx {
  @Test
  public void testResourceAllocation() throws IOException,
      YarnException, InterruptedException {
    LOG.info("--- START: testResourceAllocation ---");
        
    final int memory = 4 * 1024;
    final int vcores = 4;
    
    // Register node1
    String host1 = "host1";
    org.apache.hadoop.yarn.server.resourcemanager.NodeManager nm1 = 
      registerNode(host1, 1234, 2345, NetworkTopology.DEFAULT_RACK, 
          Resources.createResource(memory, vcores));
    
    // Register node2
    String host2 = "host2";
    org.apache.hadoop.yarn.server.resourcemanager.NodeManager nm2 = 
      registerNode(host2, 1234, 2345, NetworkTopology.DEFAULT_RACK, 
          Resources.createResource(memory/2, vcores/2));

    // Submit an application
    Application application = new Application("user1", resourceManager);
    application.submit();
    
    application.addNodeManager(host1, 1234, nm1);
    application.addNodeManager(host2, 1234, nm2);
    
    // Application resource requirements
    final int memory1 = 1024;
    Resource capability1 = Resources.createResource(memory1, 1);
    Priority priority1 = 
      org.apache.hadoop.yarn.server.resourcemanager.resource.Priority.create(1);
    application.addResourceRequestSpec(priority1, capability1);
    
    Task t1 = new Task(application, priority1, new String[] {host1, host2});
    application.addTask(t1);
    
    final int memory2 = 2048;
    Resource capability2 = Resources.createResource(memory2, 1);
    Priority priority0 = 
        org.apache.hadoop.yarn.server.resourcemanager.resource.Priority.create(0); // higher
    application.addResourceRequestSpec(priority0, capability2);
    
    // Send resource requests to the scheduler
    application.schedule();

   // Send a heartbeat to kick the tires on the Scheduler
    nodeUpdate(nm1);
    
    // Get allocations from the scheduler
    application.schedule();
    
    checkResourceUsage(nm1, nm2);
    
    LOG.info("Adding new tasks...");
    
    Task t2 = new Task(application, priority1, new String[] {host1, host2});
    application.addTask(t2);

    Task t3 = new Task(application, priority0, new String[] {ResourceRequest.ANY});
    application.addTask(t3);

    // Send resource requests to the scheduler
    application.schedule();
    checkResourceUsage(nm1, nm2);
    
    // Send heartbeats to kick the tires on the Scheduler
    nodeUpdate(nm2);
    nodeUpdate(nm2);
    nodeUpdate(nm1);
    nodeUpdate(nm1);
    
    // Get allocations from the scheduler
    LOG.info("Trying to allocate...");
    application.schedule();

    checkResourceUsage(nm1, nm2);
    
    // Complete tasks
    LOG.info("Finishing up tasks...");
    application.finishTask(t1);
    application.finishTask(t2);
    application.finishTask(t3);
    
    // Notify scheduler application is finished.
    AppAttemptRemovedSchedulerEvent appRemovedEvent1 =
        new AppAttemptRemovedSchedulerEvent(
          application.getApplicationAttemptId(), RMAppAttemptState.FINISHED, false);
    resourceManager.getResourceScheduler().handle(appRemovedEvent1);
    
    checkResourceUsage(nm1, nm2);
    
    LOG.info("--- END: testResourceAllocation ---");
  }

};