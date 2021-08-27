//,temp,AtomixQueueConsumer.java,47,62,temp,AtomixSetConsumer.java,47,62
//,2
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        this.queue = getAtomixEndpoint()
                .getAtomix()
                .getQueue(
                        resourceName,
                        new DistributedQueue.Config(getAtomixEndpoint().getConfiguration().getResourceOptions(resourceName)),
                        new DistributedQueue.Options(getAtomixEndpoint().getConfiguration().getResourceConfig(resourceName)))
                .join();

        LOG.debug("Subscribe to events for queue: {}", resourceName);
        this.listeners.add(this.queue.onAdd(this::onEvent).join());
        this.listeners.add(this.queue.onRemove(this::onEvent).join());
    }

};