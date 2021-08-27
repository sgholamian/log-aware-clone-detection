//,temp,EventbridgeProducer.java,385,416,temp,EventbridgeProducer.java,253,284
//,2
public class xxx {
    private void describeRule(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DescribeRuleRequest) {
                DescribeRuleResponse result;
                try {
                    result = eventbridgeClient.describeRule((DescribeRuleRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Describe Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DescribeRuleRequest.Builder builder = DescribeRuleRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME))) {
                String ruleName = exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME, String.class);
                builder.name(ruleName);
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            DescribeRuleResponse result;
            try {
                result = eventbridgeClient.describeRule(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Describe Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};