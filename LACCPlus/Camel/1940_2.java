//,temp,MQ2Producer.java,335,368,temp,MQ2Producer.java,293,333
//,3
public class xxx {
    private void updateBroker(MqClient mqClient, Exchange exchange) throws InvalidPayloadException {
        String brokerId;
        ConfigurationId configurationId;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof UpdateBrokerRequest) {
                UpdateBrokerResponse result;
                try {
                    result = mqClient.updateBroker((UpdateBrokerRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Update Broker command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            UpdateBrokerRequest.Builder builder = UpdateBrokerRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MQ2Constants.BROKER_ID))) {
                brokerId = exchange.getIn().getHeader(MQ2Constants.BROKER_ID, String.class);
                builder.brokerId(brokerId);
            } else {
                throw new IllegalArgumentException("Broker Name must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MQ2Constants.CONFIGURATION_ID))) {
                configurationId = exchange.getIn().getHeader(MQ2Constants.CONFIGURATION_ID, ConfigurationId.class);
                builder.configuration(configurationId);
            } else {
                throw new IllegalArgumentException("Broker Name must be specified");
            }
            UpdateBrokerResponse result;
            try {
                result = mqClient.updateBroker(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Update Broker command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};