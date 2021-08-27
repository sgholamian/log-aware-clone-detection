//,temp,ProcessorValidator.java,53,77,temp,HystrixProcessorCommandFallbackViaNetwork.java,47,67
//,3
public class xxx {
    @Override
    public void validate(Message message, DataType type) throws ValidationException {
        Exchange exchange = message.getExchange();

        LOG.debug("Sending to validate processor '{}'", processor);
        // create a new exchange to use during validation to avoid side-effects on original exchange
        Exchange copy = ExchangeHelper.createCorrelatedCopy(exchange, false, true);
        try {
            processor.process(copy);

            // if the validation failed then propagate the exception
            if (copy.getException() != null) {
                exchange.setException(copy.getException());
            } else {
                // success copy result
                ExchangeHelper.copyResults(exchange, copy);
            }
        } catch (Exception e) {
            if (e instanceof ValidationException) {
                throw (ValidationException) e;
            } else {
                throw new ValidationException(String.format("Validation failed for '%s'", type), exchange, e);
            }
        }
    }

};