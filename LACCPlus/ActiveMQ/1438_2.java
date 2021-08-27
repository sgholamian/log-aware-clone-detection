//,temp,AMQ4221Test.java,147,160,temp,QueueView.java,59,65
//,3
public class xxx {
    public synchronized void purge() throws Exception {
        final long originalMessageCount = destination.getDestinationStatistics().getMessages().getCount();

        ((Queue)destination).purge();

        LOG.info("{} purge of {} messages", destination.getActiveMQDestination().getQualifiedName(), originalMessageCount);
    }

};