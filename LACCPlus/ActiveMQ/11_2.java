//,temp,TransportConnection.java,1118,1126,temp,TransportConnection.java,1101,1110
//,3
public class xxx {
    @Override
    public void stop() throws Exception {
        // do not stop task the task runner factories (taskRunnerFactory, stopTaskRunnerFactory)
        // as their lifecycle is handled elsewhere

        stopAsync();
        while (!stopped.await(5, TimeUnit.SECONDS)) {
            LOG.info("The connection to '{}' is taking a long time to shutdown.", transport.getRemoteAddress());
        }
    }

};