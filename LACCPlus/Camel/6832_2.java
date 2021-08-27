//,temp,GenericFileConsumer.java,214,259,temp,SplunkConsumer.java,105,125
//,3
public class xxx {
    @Override
    public int processBatch(Queue<Object> exchanges) throws Exception {
        int total = exchanges.size();

        for (int index = 0; index < total && isBatchAllowed(); index++) {
            Exchange exchange = ObjectHelper.cast(Exchange.class, exchanges.poll());
            exchange.setProperty(ExchangePropertyKey.BATCH_INDEX, index);
            exchange.setProperty(ExchangePropertyKey.BATCH_SIZE, total);
            exchange.setProperty(ExchangePropertyKey.BATCH_COMPLETE, index == total - 1);
            try {
                LOG.trace("Processing exchange [{}]...", exchange);
                getProcessor().process(exchange);
            } catch (Exception e) {
                exchange.setException(e);
            }
            if (exchange.getException() != null) {
                getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
            }
        }
        return total;
    }

};