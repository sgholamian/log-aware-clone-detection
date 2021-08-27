//,temp,MessageReceiverListenerImpl.java,54,70,temp,AbstractHandler.java,45,62
//,3
public class xxx {
    @Override
    public void onAcceptAlertNotification(AlertNotification alertNotification) {
        LOG.debug("Received an alertNotification {}", alertNotification);

        Exchange exchange = createOnAcceptAlertNotificationExchange(alertNotification);
        try {
            processor.process(exchange);
        } catch (Exception e) {
            exchange.setException(e);
        }

        if (exchange.getException() != null) {
            exceptionHandler.handleException("Cannot process exchange. This exception will be ignored.", exchange,
                    exchange.getException());
        }
        consumer.releaseExchange(exchange, false);
    }

};