//,temp,ErrorTest.java,121,126,temp,ErrorHandlingTest.java,140,145
//,2
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            Exception ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
            LOG.debug("Processing caught exception {}", ex.getLocalizedMessage());
            exchange.getIn().getHeaders().put("HandledError", ex.getLocalizedMessage());
        }

};