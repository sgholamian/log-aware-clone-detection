//,temp,EventbridgeProducer.java,451,483,temp,EventbridgeProducer.java,352,383
//,2
public class xxx {
    private void listRuleNamesByTarget(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListRuleNamesByTargetRequest) {
                ListRuleNamesByTargetResponse result;
                try {
                    result = eventbridgeClient.listRuleNamesByTarget((ListRuleNamesByTargetRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("List Rule Name by Targets command returned the error code {}",
                            ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ListRuleNamesByTargetRequest.Builder builder = ListRuleNamesByTargetRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.TARGET_ARN))) {
                String targetArn = exchange.getIn().getHeader(EventbridgeConstants.TARGET_ARN, String.class);
                builder.targetArn(targetArn);
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            ListRuleNamesByTargetResponse result;
            try {
                result = eventbridgeClient.listRuleNamesByTarget(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("List Rule by Target command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};