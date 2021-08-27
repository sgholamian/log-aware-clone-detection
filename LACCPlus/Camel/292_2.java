//,temp,MQ2Producer.java,113,143,temp,KMS2Producer.java,111,141
//,2
public class xxx {
    private void listKeys(KmsClient kmsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListKeysRequest) {
                ListKeysResponse result;
                try {
                    result = kmsClient.listKeys((ListKeysRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("List Keys command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ListKeysRequest.Builder builder = ListKeysRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(KMS2Constants.LIMIT))) {
                int limit = exchange.getIn().getHeader(KMS2Constants.LIMIT, Integer.class);
                builder.limit(limit);
            }
            ListKeysResponse result;
            try {
                result = kmsClient.listKeys(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("List Keys command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};