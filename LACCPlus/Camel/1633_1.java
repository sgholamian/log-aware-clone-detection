//,temp,STS2Producer.java,163,196,temp,STS2Producer.java,93,132
//,3
public class xxx {
    private void getFederationToken(StsClient stsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof GetFederationTokenRequest) {
                GetFederationTokenResponse result;
                try {
                    GetFederationTokenRequest request = (GetFederationTokenRequest) payload;
                    result = stsClient.getFederationToken(request);
                } catch (AwsServiceException ase) {
                    LOG.trace("Get Federation Token command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            GetFederationTokenRequest.Builder builder = GetFederationTokenRequest.builder();
            GetFederationTokenResponse result;
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(STS2Constants.FEDERATED_NAME))) {
                String federatedName = exchange.getIn().getHeader(STS2Constants.FEDERATED_NAME, String.class);
                builder.name(federatedName);
            } else {
                throw new IllegalArgumentException("Federated name needs to be specified for assumeRole operation");
            }
            try {
                result = stsClient.getFederationToken(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Get Federation Token command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};