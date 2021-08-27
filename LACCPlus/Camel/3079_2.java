//,temp,DelayProcessorSupport.java,72,81,temp,TelegramServiceRestBotAPIAdapter.java,402,409
//,3
public class xxx {
        @Override
        public void onThrowable(Throwable t) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("{} onThrowable {}", exchange.getExchangeId(), t.getMessage(), t);
            }
            exchange.setException(t);
            callback.done(false);
        }

};