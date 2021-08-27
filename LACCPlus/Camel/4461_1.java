//,temp,MessageReceiverListenerImpl.java,99,120,temp,MessageReceiverListenerImpl.java,72,97
//,3
public class xxx {
    @Override
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session session) throws ProcessRequestException {
        LOG.debug("Received a dataSm {}", dataSm);

        MessageId newMessageId = messageIDGenerator.newMessageId();
        Exchange exchange = endpoint.createOnAcceptDataSm(dataSm, newMessageId.getValue());
        try {
            processor.process(exchange);
        } catch (Exception e) {
            exchange.setException(e);
        }

        if (exchange.getException() != null) {
            ProcessRequestException pre = exchange.getException(ProcessRequestException.class);
            if (pre == null) {
                pre = new ProcessRequestException(exchange.getException().getMessage(), 255, exchange.getException());
            }
            throw pre;
        }

        return new DataSmResult(newMessageId, dataSm.getOptionalParameters());
    }

};