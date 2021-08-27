//,temp,ConcurrentJobRequestsTestBase.java,115,142,temp,ConcurrentJobRequestsTestBase.java,87,113
//,3
public class xxx {
  public JobRunnable ConcurrentListJobs(final int threadCount, AppConfig config,
         final boolean killThreads, boolean interruptThreads, final Answer<List<JobItemBean>> answer)
         throws IOException, InterruptedException, QueueException, NotAuthorizedException,
         BadParam, BusyException {

    ListDelegator delegator = new ListDelegator(config);
    final ListDelegator mockDelegator = Mockito.spy(delegator);

    doAnswer(answer).when(mockDelegator).listJobs(any(String.class),
                             any(boolean.class), any(String.class),
                             any(int.class), any(boolean.class));

    JobRunnable listJobRunnable = new JobRunnable() {
      @Override
      public void run() {
        try {
          int threadId = waitForAllThreadsToStart(this, threadCount);
          LOG.info("Started executing Job List operation. ThreadId : " + threadId);
          mockDelegator.run("admin", true, "", 10, true);
        } catch (Exception ex) {
          exception = ex;
        }
      }
    };

    executeJobOperations(listJobRunnable, threadCount, killThreads, interruptThreads);
    return listJobRunnable;
  }

};