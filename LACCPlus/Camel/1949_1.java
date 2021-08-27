//,temp,MQ2Producer.java,223,256,temp,STS2Producer.java,134,161
//,3
public class xxx {
    private void deleteBroker(MqClient mqClient, Exchange exchange) throws InvalidPayloadException {
        String brokerId;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DeleteBrokerRequest) {
                DeleteBrokerResponse result;
                try {
                    result = mqClient.deleteBroker((DeleteBrokerRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Delete Broker command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DeleteBrokerRequest.Builder builder = DeleteBrokerRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MQ2Constants.BROKER_ID))) {
                brokerId = exchange.getIn().getHeader(MQ2Constants.BROKER_ID, String.class);
                builder.brokerId(brokerId);
            } else {
                throw new IllegalArgumentException("Broker Name must be specified");
            }
            DeleteBrokerResponse result;
            try {
                result = mqClient.deleteBroker(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Delete Broker command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};