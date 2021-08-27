//,temp,Lambda2Producer.java,569,595,temp,EventbridgeProducer.java,214,251
//,3
public class xxx {
    private void removeTargets(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof RemoveTargetsRequest) {
                RemoveTargetsResponse result;
                try {
                    result = eventbridgeClient.removeTargets((RemoveTargetsRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("RemoveTargets command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            RemoveTargetsRequest.Builder builder = RemoveTargetsRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME))) {
                String ruleName = exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME, String.class);
                builder.rule(ruleName);
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.TARGETS_IDS))) {
                Collection<String> ids = exchange.getIn().getHeader(EventbridgeConstants.TARGETS_IDS, Collection.class);
                builder.ids(ids);
            } else {
                throw new IllegalArgumentException("At least one targets must be specified");
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            RemoveTargetsResponse result;
            try {
                result = eventbridgeClient.removeTargets(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Remove Targets command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};