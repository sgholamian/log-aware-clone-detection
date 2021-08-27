//,temp,ThreadsProcessor.java,90,99,temp,ThreadsProcessor.java,81,88
//,3
public class xxx {
        @Override
        public void run() {
            LOG.trace("Continue routing exchange {}", exchange);
            if (shutdown.get()) {
                exchange.setException(new RejectedExecutionException("ThreadsProcessor is not running."));
            }
            callback.done(done);
        }

};