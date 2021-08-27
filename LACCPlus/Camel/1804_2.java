//,temp,EKS2Producer.java,97,127,temp,EventbridgeProducer.java,319,350
//,3
public class xxx {
    private void disableRule(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DisableRuleRequest) {
                DisableRuleResponse result;
                try {
                    result = eventbridgeClient.disableRule((DisableRuleRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Disable Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DisableRuleRequest.Builder builder = DisableRuleRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME))) {
                String ruleName = exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME, String.class);
                builder.name(ruleName);
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            DisableRuleResponse result;
            try {
                result = eventbridgeClient.disableRule(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Disable Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};