//,temp,CamelItemReader.java,51,58,temp,CamelItemProcessor.java,41,48
//,3
public class xxx {
    @Override
    @SuppressWarnings("unchecked")
    public I read() throws Exception {
        LOG.debug("reading new item...");
        I item = (I) consumerTemplate.receiveBody(endpointUri);
        LOG.debug("read item [{}]", item);
        return item;
    }

};