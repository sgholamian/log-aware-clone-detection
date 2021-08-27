//,temp,InactivityMonitorTest.java,193,200,temp,InactivityMonitorTest.java,85,92
//,2
public class xxx {
            @Override
            public void onException(IOException error) {
                if (!ignoreClientError.get()) {
                    LOG.info("Client transport error:");
                    error.printStackTrace();
                    clientErrorCount.incrementAndGet();
                }
            }

};