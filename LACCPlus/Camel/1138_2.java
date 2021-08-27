//,temp,AtomixMessagingConsumer.java,65,79,temp,AtomixValueConsumer.java,47,61
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        this.value = getAtomixEndpoint()
                .getAtomix()
                .getValue(
                        resourceName,
                        new DistributedValue.Config(getAtomixEndpoint().getConfiguration().getResourceOptions(resourceName)),
                        new DistributedValue.Options(getAtomixEndpoint().getConfiguration().getResourceConfig(resourceName)))
                .join();

        LOG.debug("Subscribe to events for value: {}", resourceName);
        this.listeners.add(this.value.onChange(this::onEvent).join());
    }

};