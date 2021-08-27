//,temp,MQ2Producer.java,113,143,temp,KMS2Producer.java,111,141
//,2
public class xxx {
    private void listBrokers(MqClient mqClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListBrokersRequest) {
                ListBrokersResponse result;
                try {
                    result = mqClient.listBrokers((ListBrokersRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("List Brokers command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ListBrokersRequest.Builder builder = ListBrokersRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(MQ2Constants.MAX_RESULTS))) {
                int maxResults = exchange.getIn().getHeader(MQ2Constants.MAX_RESULTS, Integer.class);
                builder.maxResults(maxResults);
            }
            ListBrokersResponse result;
            try {
                result = mqClient.listBrokers(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("List Brokers command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};