//,temp,BeanstalkConsumer.java,231,238,temp,BeanstalkConsumer.java,222,229
//,3
public class xxx {
        @Override
        public void onFailure(final Exchange exchange) {
            try {
                executor.submit(new RunCommand(failureCommand, exchange)).get();
            } catch (Exception e) {
                LOG.error("{} could not run failure of exchange {}", failureCommand.getClass().getName(), exchange, e);
            }
        }

};