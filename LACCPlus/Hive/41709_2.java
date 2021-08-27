//,temp,ConcurrentJobRequestsTestBase.java,171,180,temp,ConcurrentJobRequestsTestBase.java,128,137
//,3
public class xxx {
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