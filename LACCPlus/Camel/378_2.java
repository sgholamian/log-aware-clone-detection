//,temp,BeanstalkConsumer.java,231,238,temp,BeanstalkConsumer.java,222,229
//,3
public class xxx {
        @Override
        public void onComplete(final Exchange exchange) {
            try {
                executor.submit(new RunCommand(successCommand, exchange)).get();
            } catch (Exception e) {
                LOG.error("Could not run completion of exchange {}", exchange, e);
            }
        }

};