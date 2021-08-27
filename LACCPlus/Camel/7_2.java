//,temp,DropboxScheduledPollConsumer.java,59,67,temp,DropboxProducer.java,50,58
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        if (configuration.getClient() == null) {
            configuration.setClient(null);

            LOG.debug("Producer DropBox client deleted");
        }
        super.doStop();
    }

};