//,temp,LevelDBFile.java,206,215,temp,PulsarProducer.java,118,125
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        LOG.debug("Stopping producer: {}", this);
        if (producer != null) {
            producer.close();
            producer = null;
        }
    }

};