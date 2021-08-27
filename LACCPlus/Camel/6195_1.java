//,temp,XQueryBuilder.java,230,240,temp,XQueryBuilder.java,220,228
//,3
public class xxx {
    public byte[] evaluateAsBytes(Exchange exchange) throws Exception {
        LOG.debug("evaluateAsBytes: {} for exchange: {}", expression, exchange);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Result result = new StreamResult(buffer);
        getExpression().pull(createDynamicContext(exchange), result, properties);

        byte[] answer = buffer.toByteArray();
        buffer.close();
        return answer;
    }

};