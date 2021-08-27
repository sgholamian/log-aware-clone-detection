//,temp,ConcurrentJobRequestsTestBase.java,115,142,temp,ConcurrentJobRequestsTestBase.java,87,113
//,3
public class xxx {
  public JobRunnable ConcurrentJobsStatus(final int threadCount, AppConfig appConfig,
         final boolean killThreads, boolean interruptThreads, final Answer<QueueStatusBean> answer)
         throws IOException, InterruptedException, QueueException, NotAuthorizedException,
         BadParam, BusyException {

    StatusDelegator delegator = new StatusDelegator(appConfig);
    final StatusDelegator mockDelegator = Mockito.spy(delegator);

    doAnswer(answer).when(mockDelegator).getJobStatus(any(String.class),
                             any(String.class));

    JobRunnable statusJobRunnable = new JobRunnable() {
      @Override
      public void run() {
        try {
          int threadId = waitForAllThreadsToStart(this, threadCount);
          LOG.info("Started executing Job Status operation. ThreadId : " + threadId);
          mockDelegator.run("admin", "job_1000" + threadId);
        } catch (Exception ex) {
          exception = ex;
        }
      }
    };

    executeJobOperations(statusJobRunnable, threadCount, killThreads, interruptThreads);
    return statusJobRunnable;
  }

};