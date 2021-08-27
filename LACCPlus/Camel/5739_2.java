//,temp,RecipientListProcessor.java,383,391,temp,DefaultConsumer.java,188,197
//,3
public class xxx {
    @Override
    protected void doBuild() throws Exception {
        LOG.debug("Build consumer: {}", this);
        ServiceHelper.buildService(exchangeFactory, processor);

        // force to create and load the class during build time so the JVM does not
        // load the class on first exchange to be created
        Object dummy = new DefaultConsumerCallback(this, null, false);
        LOG.trace("Warming up DefaultConsumer loaded class: {}", dummy.getClass().getName());
    }

};