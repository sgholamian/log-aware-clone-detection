//,temp,AhcProducer.java,150,161,temp,AhcProducer.java,87,99
//,3
public class xxx {
        @Override
        public State onHeadersReceived(HttpHeaders headers) throws Exception {
            if (LOG.isTraceEnabled()) {
                LOG.trace("{} onHeadersReceived {}", exchange.getExchangeId(), headers);
            }
            try {
                getEndpoint().getBinding().onHeadersReceived(getEndpoint(), exchange, headers);
            } catch (Exception e) {
                exchange.setException(e);
            }
            return State.CONTINUE;
        }

};