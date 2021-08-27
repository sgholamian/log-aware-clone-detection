//,temp,AtomixQueueConsumer.java,47,62,temp,AtomixSetConsumer.java,47,62
//,2
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        this.set = getAtomixEndpoint()
                .getAtomix()
                .getSet(
                        resourceName,
                        new DistributedSet.Config(getAtomixEndpoint().getConfiguration().getResourceOptions(resourceName)),
                        new DistributedSet.Options(getAtomixEndpoint().getConfiguration().getResourceConfig(resourceName)))
                .join();

        LOG.debug("Subscribe to events for set: {}", resourceName);
        this.listeners.add(this.set.onAdd(this::onEvent).join());
        this.listeners.add(this.set.onRemove(this::onEvent).join());
    }

};