//,temp,CamelItemReader.java,51,58,temp,CamelItemProcessor.java,41,48
//,3
public class xxx {
    @Override
    @SuppressWarnings("unchecked")
    public O process(I i) throws Exception {
        LOG.debug("processing item [{}]...", i);
        O result = (O) producerTemplate.requestBody(endpointUri, i);
        LOG.debug("processed item");
        return result;
    }

};