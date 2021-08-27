//,temp,RecipientListProcessor.java,383,391,temp,DefaultConsumer.java,188,197
//,3
public class xxx {
    @Override
    protected void doBuild() throws Exception {
        super.doBuild();
        ServiceHelper.buildService(producerCache);

        // eager load classes
        Object dummy = new RecipientProcessorExchangePair(0, null, null, null, null, null, null, false);
        LOG.trace("Loaded {}", dummy.getClass().getName());
    }

};