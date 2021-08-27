//,temp,EventbridgeProducer.java,385,416,temp,EventbridgeProducer.java,253,284
//,2
public class xxx {
    private void deleteRule(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DeleteRuleRequest) {
                DeleteRuleResponse result;
                try {
                    result = eventbridgeClient.deleteRule((DeleteRuleRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Delete Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DeleteRuleRequest.Builder builder = DeleteRuleRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME))) {
                String ruleName = exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME, String.class);
                builder.name(ruleName);
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            DeleteRuleResponse result;
            try {
                result = eventbridgeClient.deleteRule(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Delete Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};