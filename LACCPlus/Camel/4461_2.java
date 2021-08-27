//,temp,MessageReceiverListenerImpl.java,99,120,temp,MessageReceiverListenerImpl.java,72,97
//,3
public class xxx {
    @Override
    public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException {
        LOG.debug("Received a deliverSm {}", deliverSm);

        Exchange exchange;
        try {
            exchange = endpoint.createOnAcceptDeliverSmExchange(deliverSm);
        } catch (Exception e) {
            exceptionHandler.handleException("Cannot create exchange. This exception will be ignored.", e);
            return;
        }

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
    }

};