//,temp,TestEditLogRace.java,485,498,temp,TestEditLogRace.java,389,403
//,2
public class xxx {
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
          LOG.info("logSync called");
          if (Thread.currentThread() == doAnEditThread) {
            LOG.info("edit thread: Telling main thread we made it just before logSync...");
            waitToEnterSync.countDown();
            LOG.info("edit thread: sleeping for " + BLOCK_TIME + "secs");
            Thread.sleep(BLOCK_TIME*1000);
            LOG.info("Going through to logSync. This will allow the main thread to continue.");
          }
          invocation.callRealMethod();
          LOG.info("logSync complete");
          return null;
        }

};