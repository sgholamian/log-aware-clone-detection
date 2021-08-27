//,temp,IrcProducer.java,107,117,temp,IrcConsumer.java,46,56
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        if (connection != null) {
            for (IrcChannel channel : endpoint.getConfiguration().getChannelList()) {
                LOG.debug("Parting: {}", channel);
                connection.doPart(channel.getName());
            }
            connection.removeIRCEventListener(listener);
        }
        super.doStop();
    }

};