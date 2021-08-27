//,temp,ProcessorValidator.java,53,77,temp,HystrixProcessorCommandFallbackViaNetwork.java,47,67
//,3
public class xxx {
    @Override
    protected Message run() throws Exception {
        LOG.debug("Running fallback processor: {} with exchange: {}", processor, exchange);

        try {
            // process the processor until its fully done
            // (we do not hav any hystrix callback to leverage so we need to complete all work in this run method)
            processor.process(exchange);
        } catch (Throwable e) {
            exchange.setException(e);
        }

        // if we failed then throw an exception to signal that the fallback failed as well
        if (exchange.getException() != null) {
            throw exchange.getException();
        }

        LOG.debug("Running fallback processor: {} with exchange: {} done", processor, exchange);
        // no fallback then we are done
        return exchange.getMessage();
    }

};