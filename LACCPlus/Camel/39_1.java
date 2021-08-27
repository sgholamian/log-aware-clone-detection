//,temp,ErrorTest.java,137,142,temp,ErrorHandlingTest.java,156,161
//,2
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            Exception ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
            LOG.debug(">> Attempting redelivery of handled exception {} with message: {}",
                    ex.getClass().getSimpleName(), ex.getLocalizedMessage());
        }

};