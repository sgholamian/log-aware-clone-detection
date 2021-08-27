//,temp,KMS2Producer.java,209,245,temp,EKS2Producer.java,129,167
//,3
public class xxx {
    private void scheduleKeyDeletion(KmsClient kmsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ScheduleKeyDeletionRequest) {
                ScheduleKeyDeletionResponse result;
                try {
                    result = kmsClient.scheduleKeyDeletion((ScheduleKeyDeletionRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Schedule Key Deletion command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ScheduleKeyDeletionRequest.Builder builder = ScheduleKeyDeletionRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(KMS2Constants.KEY_ID))) {
                String keyId = exchange.getIn().getHeader(KMS2Constants.KEY_ID, String.class);
                builder.keyId(keyId);
            } else {
                throw new IllegalArgumentException("Key Id must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(KMS2Constants.PENDING_WINDOW_IN_DAYS))) {
                int pendingWindows = exchange.getIn().getHeader(KMS2Constants.PENDING_WINDOW_IN_DAYS, Integer.class);
                builder.pendingWindowInDays(pendingWindows);
            }
            ScheduleKeyDeletionResponse result;
            try {
                result = kmsClient.scheduleKeyDeletion(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Schedule Key Deletion command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};