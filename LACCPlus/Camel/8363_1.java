//,temp,ThreadsProcessor.java,90,99,temp,ThreadsProcessor.java,81,88
//,3
public class xxx {
        @Override
        public void reject() {
            // reject should mark the exchange with an rejected exception and mark not to route anymore
            exchange.setException(new RejectedExecutionException());
            LOG.trace("Rejected routing exchange {}", exchange);
            if (shutdown.get()) {
                exchange.setException(new RejectedExecutionException("ThreadsProcessor is not running."));
            }
            callback.done(done);
        }

};