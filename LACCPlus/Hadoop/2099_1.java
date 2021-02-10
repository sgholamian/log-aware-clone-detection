//,temp,TestLocalContainerLauncher.java,99,178,temp,TestContainerLauncherImpl.java,328,405
//,3
public class xxx {
  @SuppressWarnings("rawtypes")
  @Test(timeout=10000)
  public void testKillJob() throws Exception {
    JobConf conf = new JobConf();
    AppContext context = mock(AppContext.class);
    // a simple event handler solely to detect the container cleaned event
    final CountDownLatch isDone = new CountDownLatch(1);
    EventHandler handler = new EventHandler() {
      @Override
      public void handle(Event event) {
        LOG.info("handling event " + event.getClass() +
            " with type " + event.getType());
        if (event instanceof TaskAttemptEvent) {
          if (event.getType() == TaskAttemptEventType.TA_CONTAINER_CLEANED) {
            isDone.countDown();
          }
        }
      }
    };
    when(context.getEventHandler()).thenReturn(handler);

    // create and start the launcher
    LocalContainerLauncher launcher =
        new LocalContainerLauncher(context, mock(TaskUmbilicalProtocol.class));
    launcher.init(conf);
    launcher.start();

    // create mocked job, task, and task attempt
    // a single-mapper job
    JobId jobId = MRBuilderUtils.newJobId(System.currentTimeMillis(), 1, 1);
    TaskId taskId = MRBuilderUtils.newTaskId(jobId, 1, TaskType.MAP);
    TaskAttemptId taId = MRBuilderUtils.newTaskAttemptId(taskId, 0);

    Job job = mock(Job.class);
    when(job.getTotalMaps()).thenReturn(1);
    when(job.getTotalReduces()).thenReturn(0);
    Map<JobId,Job> jobs = new HashMap<JobId,Job>();
    jobs.put(jobId, job);
    // app context returns the one and only job
    when(context.getAllJobs()).thenReturn(jobs);

    org.apache.hadoop.mapreduce.v2.app.job.Task ytask =
        mock(org.apache.hadoop.mapreduce.v2.app.job.Task.class);
    when(ytask.getType()).thenReturn(TaskType.MAP);
    when(job.getTask(taskId)).thenReturn(ytask);

    // create a sleeping mapper that runs beyond the test timeout
    MapTask mapTask = mock(MapTask.class);
    when(mapTask.isMapOrReduce()).thenReturn(true);
    when(mapTask.isMapTask()).thenReturn(true);
    TaskAttemptID taskID = TypeConverter.fromYarn(taId);
    when(mapTask.getTaskID()).thenReturn(taskID);
    when(mapTask.getJobID()).thenReturn(taskID.getJobID());
    doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        // sleep for a long time
        LOG.info("sleeping for 5 minutes...");
        Thread.sleep(5*60*1000);
        return null;
      }
    }).when(mapTask).run(isA(JobConf.class), isA(TaskUmbilicalProtocol.class));

    // pump in a task attempt launch event
    ContainerLauncherEvent launchEvent =
        new ContainerRemoteLaunchEvent(taId, null, createMockContainer(), mapTask);
    launcher.handle(launchEvent);

    Thread.sleep(200);
    // now pump in a container clean-up event
    ContainerLauncherEvent cleanupEvent =
        new ContainerLauncherEvent(taId, null, null, null,
            ContainerLauncher.EventType.CONTAINER_REMOTE_CLEANUP);
    launcher.handle(cleanupEvent);

    // wait for the event to fire: this should be received promptly
    isDone.await();

    launcher.close();
  }

};