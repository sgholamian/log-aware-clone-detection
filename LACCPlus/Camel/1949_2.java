//,temp,MQ2Producer.java,223,256,temp,STS2Producer.java,134,161
//,3
public class xxx {
    private void getSessionToken(StsClient stsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof GetSessionTokenRequest) {
                GetSessionTokenResponse result;
                try {
                    GetSessionTokenRequest request = (GetSessionTokenRequest) payload;
                    result = stsClient.getSessionToken(request);
                } catch (AwsServiceException ase) {
                    LOG.trace("Get Session Token command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            GetSessionTokenRequest.Builder builder = GetSessionTokenRequest.builder();
            GetSessionTokenResponse result;
            try {
                result = stsClient.getSessionToken(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Get Session Token command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};