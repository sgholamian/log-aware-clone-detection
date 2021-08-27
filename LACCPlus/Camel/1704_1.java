//,temp,EventbridgeProducer.java,286,317,temp,EventbridgeProducer.java,132,173
//,3
public class xxx {
    private void enableRule(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof EnableRuleRequest) {
                EnableRuleResponse result;
                try {
                    result = eventbridgeClient.enableRule((EnableRuleRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Enable Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            EnableRuleRequest.Builder builder = EnableRuleRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME))) {
                String ruleName = exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME, String.class);
                builder.name(ruleName);
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            EnableRuleResponse result;
            try {
                result = eventbridgeClient.enableRule(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Enable Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};