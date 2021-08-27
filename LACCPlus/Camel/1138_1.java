//,temp,AtomixMessagingConsumer.java,65,79,temp,AtomixValueConsumer.java,47,61
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        DistributedGroup group = getAtomixEndpoint().getAtomix().getGroup(
                groupName,
                new DistributedGroup.Config(getAtomixEndpoint().getConfiguration().getResourceOptions(groupName)),
                new DistributedGroup.Options(getAtomixEndpoint().getConfiguration().getResourceConfig(groupName))).join();

        this.localMember = group.join(memberName).join();
        this.consumer = localMember.messaging().consumer(channelName);

        LOG.debug("Subscribe to group: {}, member: {}, channel: {}", groupName, memberName, channelName);
        this.listeners.add(consumer.onMessage(this::onMessage));
    }

};