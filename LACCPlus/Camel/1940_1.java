//,temp,MQ2Producer.java,335,368,temp,MQ2Producer.java,293,333
//,3
public class xxx {
    private void describeBroker(MqClient mqClient, Exchange exchange) throws InvalidPayloadException {
        String brokerId;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DescribeBrokerRequest) {
                DescribeBrokerResponse result;
                try {
                    result = mqClient.describeBroker((DescribeBrokerRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Reboot Broker command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DescribeBrokerRequest.Builder builder = DescribeBrokerRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MQ2Constants.BROKER_ID))) {
                brokerId = exchange.getIn().getHeader(MQ2Constants.BROKER_ID, String.class);
                builder.brokerId(brokerId);
            } else {
                throw new IllegalArgumentException("Broker Name must be specified");
            }
            DescribeBrokerResponse result;
            try {
                result = mqClient.describeBroker(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Reboot Broker command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};