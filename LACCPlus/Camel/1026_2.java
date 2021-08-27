//,temp,AhcProducer.java,150,161,temp,AhcProducer.java,87,99
//,3
public class xxx {
        @Override
        public void onThrowable(Throwable t) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("{} onThrowable {}", exchange.getExchangeId(), t.getMessage(), t);
            }
            try {
                getEndpoint().getBinding().onThrowable(getEndpoint(), exchange, t);
            } catch (Exception e) {
                exchange.setException(e);
            } finally {
                callback.done(false);
            }
        }

};