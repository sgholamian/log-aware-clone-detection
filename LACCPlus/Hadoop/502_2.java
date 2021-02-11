//,temp,TestRMContainerAllocator.java,924,1020,temp,TestRMContainerAllocator.java,772,897
//,3
public class xxx {
  @Test
  public void testReportedAppProgress() throws Exception {

    LOG.info("Running testReportedAppProgress");

    Configuration conf = new Configuration();
    final MyResourceManager rm = new MyResourceManager(conf);
    rm.start();
    DrainDispatcher rmDispatcher = (DrainDispatcher) rm.getRMContext()
        .getDispatcher();

    // Submit the application
    RMApp rmApp = rm.submitApp(1024);
    rmDispatcher.await();

    MockNM amNodeManager = rm.registerNode("amNM:1234", 21504);
    amNodeManager.nodeHeartbeat(true);
    rmDispatcher.await();

    final ApplicationAttemptId appAttemptId = rmApp.getCurrentAppAttempt()
        .getAppAttemptId();
    rm.sendAMLaunched(appAttemptId);
    rmDispatcher.await();

    MRApp mrApp = new MRApp(appAttemptId, ContainerId.newContainerId(
      appAttemptId, 0), 10, 10, false, this.getClass().getName(), true, 1) {
      @Override
      protected Dispatcher createDispatcher() {
        return new DrainDispatcher();
      }
      protected ContainerAllocator createContainerAllocator(
          ClientService clientService, AppContext context) {
        return new MyContainerAllocator(rm, appAttemptId, context);
      };
    };

    Assert.assertEquals(0.0, rmApp.getProgress(), 0.0);

    mrApp.submit(conf);
    Job job = mrApp.getContext().getAllJobs().entrySet().iterator().next()
        .getValue();

    DrainDispatcher amDispatcher = (DrainDispatcher) mrApp.getDispatcher();

    MyContainerAllocator allocator = (MyContainerAllocator) mrApp
      .getContainerAllocator();

    mrApp.waitForInternalState((JobImpl) job, JobStateInternal.RUNNING);

    amDispatcher.await();
    // Wait till all map-attempts request for containers
    for (Task t : job.getTasks().values()) {
      if (t.getType() == TaskType.MAP) {
        mrApp.waitForInternalState((TaskAttemptImpl) t.getAttempts().values()
            .iterator().next(), TaskAttemptStateInternal.UNASSIGNED);
      }
    }
    amDispatcher.await();

    allocator.schedule();
    rmDispatcher.await();
    amNodeManager.nodeHeartbeat(true);
    rmDispatcher.await();
    allocator.schedule();
    rmDispatcher.await();

    // Wait for all map-tasks to be running
    for (Task t : job.getTasks().values()) {
      if (t.getType() == TaskType.MAP) {
        mrApp.waitForState(t, TaskState.RUNNING);
      }
    }

    allocator.schedule(); // Send heartbeat
    rmDispatcher.await();
    Assert.assertEquals(0.05f, job.getProgress(), 0.001f);
    Assert.assertEquals(0.05f, rmApp.getProgress(), 0.001f);

    // Finish off 1 map.
    Iterator<Task> it = job.getTasks().values().iterator();
    finishNextNTasks(rmDispatcher, amNodeManager, mrApp, it, 1);
    allocator.schedule();
    rmDispatcher.await();
    Assert.assertEquals(0.095f, job.getProgress(), 0.001f);
    Assert.assertEquals(0.095f, rmApp.getProgress(), 0.001f);

    // Finish off 7 more so that map-progress is 80%
    finishNextNTasks(rmDispatcher, amNodeManager, mrApp, it, 7);
    allocator.schedule();
    rmDispatcher.await();
    Assert.assertEquals(0.41f, job.getProgress(), 0.001f);
    Assert.assertEquals(0.41f, rmApp.getProgress(), 0.001f);

    // Finish off the 2 remaining maps
    finishNextNTasks(rmDispatcher, amNodeManager, mrApp, it, 2);

    allocator.schedule();
    rmDispatcher.await();
    amNodeManager.nodeHeartbeat(true);
    rmDispatcher.await();
    allocator.schedule();
    rmDispatcher.await();

    // Wait for all reduce-tasks to be running
    for (Task t : job.getTasks().values()) {
      if (t.getType() == TaskType.REDUCE) {
        mrApp.waitForState(t, TaskState.RUNNING);
      }
    }

    // Finish off 2 reduces
    finishNextNTasks(rmDispatcher, amNodeManager, mrApp, it, 2);

    allocator.schedule();
    rmDispatcher.await();
    Assert.assertEquals(0.59f, job.getProgress(), 0.001f);
    Assert.assertEquals(0.59f, rmApp.getProgress(), 0.001f);

    // Finish off the remaining 8 reduces.
    finishNextNTasks(rmDispatcher, amNodeManager, mrApp, it, 8);
    allocator.schedule();
    rmDispatcher.await();
    // Remaining is JobCleanup
    Assert.assertEquals(0.95f, job.getProgress(), 0.001f);
    Assert.assertEquals(0.95f, rmApp.getProgress(), 0.001f);
  }

};