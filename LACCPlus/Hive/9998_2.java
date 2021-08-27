//,temp,ACIDBenchmarks.java,184,191,temp,ACIDBenchmarks.java,114,121
//,2
public class xxx {
      @TearDown
      public void doTearDown() throws Exception {
        client.abortTxns(openTxns);
        if (BenchmarkUtils.checkTxnsCleaned(client, openTxns) == false) {
          LOG.error("Something went wrong with the cleanup of txns");
        }
        LOG.debug("aborted all opened txns");
      }

};