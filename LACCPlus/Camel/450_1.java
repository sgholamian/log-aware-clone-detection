//,temp,PulsarLocalContainerService.java,55,63,temp,NsqLocalContainerService.java,79,89
//,3
public class xxx {
    @Override
    public void initialize() {
        LOG.info("Trying to start the Pulsar container");
        container.start();

        registerProperties();
        LOG.info("Pulsar instance running at {}", getPulsarAdminUrl());
        LOG.info("Pulsar admin URL available at {}", getPulsarAdminUrl());
    }

};