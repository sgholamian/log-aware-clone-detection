//,temp,AhcProducer.java,134,148,temp,TelegramServiceRestBotAPIAdapter.java,468,476
//,3
public class xxx {
        @Override
        public State onStatusReceived(HttpResponseStatus responseStatus) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("{} onStatusReceived {}", exchange.getExchangeId(), responseStatus);
            }
            statusCode = responseStatus.getStatusCode();
            statusText = responseStatus.getStatusText();
            return State.CONTINUE;
        }

};