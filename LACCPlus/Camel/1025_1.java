//,temp,AhcProducer.java,134,148,temp,TelegramServiceRestBotAPIAdapter.java,468,476
//,3
public class xxx {
        @Override
        public State onStatusReceived(HttpResponseStatus responseStatus)
                throws Exception {
            if (LOG.isTraceEnabled()) {
                LOG.trace("{} onStatusReceived {}", exchange.getExchangeId(), responseStatus);
            }
            try {
                statusCode = responseStatus.getStatusCode();
                statusText = responseStatus.getStatusText();
                getEndpoint().getBinding().onStatusReceived(getEndpoint(), exchange, responseStatus);
            } catch (Exception e) {
                exchange.setException(e);
            }
            return State.CONTINUE;
        }

};