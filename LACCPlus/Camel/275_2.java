//,temp,EventbridgeProducer.java,451,483,temp,EventbridgeProducer.java,352,383
//,2
public class xxx {
    private void listRules(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListRulesRequest) {
                ListRulesResponse result;
                try {
                    result = eventbridgeClient.listRules((ListRulesRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("List Rules command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ListRulesRequest.Builder builder = ListRulesRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME_PREFIX))) {
                String ruleNamePrefix = exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME_PREFIX, String.class);
                builder.namePrefix(ruleNamePrefix);
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            ListRulesResponse result;
            try {
                result = eventbridgeClient.listRules(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Disable Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};