//,temp,TestEditLogRace.java,485,498,temp,TestEditLogRace.java,389,403
//,2
public class xxx {
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
          LOG.info("Flush called");
          if (Thread.currentThread() == doAnEditThread) {
            LOG.info("edit thread: Telling main thread we made it to flush section...");
            // Signal to main thread that the edit thread is in the racy section
            waitToEnterFlush.countDown();
            LOG.info("edit thread: sleeping for " + BLOCK_TIME + "secs");
            Thread.sleep(BLOCK_TIME*1000);
            LOG.info("Going through to flush. This will allow the main thread to continue.");
          }
          invocation.callRealMethod();
          LOG.info("Flush complete");
          return null;
        }

};