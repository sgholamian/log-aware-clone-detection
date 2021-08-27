//,temp,ECS2Producer.java,200,234,temp,MQ2Producer.java,258,291
//,3
public class xxx {
    private void rebootBroker(MqClient mqClient, Exchange exchange) throws InvalidPayloadException {
        String brokerId;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof RebootBrokerRequest) {
                RebootBrokerResponse result;
                try {
                    result = mqClient.rebootBroker((RebootBrokerRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Reboot Broker command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            RebootBrokerRequest.Builder builder = RebootBrokerRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MQ2Constants.BROKER_ID))) {
                brokerId = exchange.getIn().getHeader(MQ2Constants.BROKER_ID, String.class);
                builder.brokerId(brokerId);
            } else {
                throw new IllegalArgumentException("Broker Name must be specified");
            }
            RebootBrokerResponse result;
            try {
                result = mqClient.rebootBroker(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Reboot Broker command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};