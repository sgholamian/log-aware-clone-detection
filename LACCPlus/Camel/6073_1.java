//,temp,PooledExchangeFactory.java,105,132,temp,PooledProcessorExchangeFactory.java,135,160
//,3
public class xxx {
    @Override
    public boolean release(Exchange exchange) {
        try {
            // done exchange before returning back to pool
            PooledExchange ee = (PooledExchange) exchange;
            boolean force = !ee.isAutoRelease();
            ee.done(force);
            ee.onDone(null);

            // only release back in pool if reset was success
            boolean inserted = pool.offer(exchange);

            if (statisticsEnabled) {
                if (inserted) {
                    statistics.released.increment();
                } else {
                    statistics.discarded.increment();
                }
            }
            return inserted;
        } catch (Exception e) {
            if (statisticsEnabled) {
                statistics.discarded.increment();
            }
            LOG.debug("Error resetting exchange: {}. This exchange is discarded.", exchange);
            return false;
        }
    }

};