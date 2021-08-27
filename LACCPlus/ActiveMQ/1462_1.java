//,temp,LockableServiceSupport.java,157,168,temp,DefaultIOExceptionHandler.java,181,192
//,3
public class xxx {
    protected void stopBroker() {
        // we can no longer keep the lock so lets fail
        LOG.error("{}, no longer able to keep the exclusive lock so giving up being a master", brokerService.getBrokerName());
        try {
            if( brokerService.isRestartAllowed() ) {
                brokerService.requestRestart();
            }
            brokerService.stop();
        } catch (Exception e) {
            LOG.warn("Failure occurred while stopping broker");
        }
    }

};