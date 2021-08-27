//,temp,PulsarLocalContainerService.java,55,63,temp,NsqLocalContainerService.java,79,89
//,3
public class xxx {
    @Override
    public void initialize() {
        LOG.info("Trying to start the Nsq container");
        nsqLookupContainer.start();
        nsqContainer.start();

        registerProperties();

        LOG.info("Nsq producer accessible via {}", getNsqProducerUrl());
        LOG.info("Nsq consumer accessible via {}", getNsqConsumerUrl());
    }

};