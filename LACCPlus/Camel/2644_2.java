//,temp,DropboxScheduledPollConsumer.java,44,54,temp,DropboxProducer.java,38,48
//,2
public class xxx {
    @Override
    protected void doStart() throws Exception {
        if (configuration.getClient() == null) {
            //create dropbox client
            configuration.createClient();

            LOG.debug("Producer DropBox client created");
        }

        super.doStart();
    }

};