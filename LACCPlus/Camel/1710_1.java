//,temp,EventbridgeProducer.java,418,449,temp,EventbridgeProducer.java,175,212
//,3
public class xxx {
    private void listTargetsByRule(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListTargetsByRuleRequest) {
                ListTargetsByRuleResponse result;
                try {
                    result = eventbridgeClient.listTargetsByRule((ListTargetsByRuleRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("List Targets by Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ListTargetsByRuleRequest.Builder builder = ListTargetsByRuleRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME))) {
                String ruleName = exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME, String.class);
                builder.rule(ruleName);
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            ListTargetsByRuleResponse result;
            try {
                result = eventbridgeClient.listTargetsByRule(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("List Targets by Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};