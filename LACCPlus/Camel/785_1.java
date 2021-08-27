//,temp,HttpComponent.java,920,930,temp,GrpcProducerStreamingTest.java,55,62
//,3
public class xxx {
    @Override
    public void doStop() throws Exception {
        // shutdown connection manager
        if (clientConnectionManager != null) {
            LOG.info("Shutting down ClientConnectionManager: {}", clientConnectionManager);
            clientConnectionManager.shutdown();
            clientConnectionManager = null;
        }

        super.doStop();
    }

};