//,temp,EventbridgeProducer.java,418,449,temp,EventbridgeProducer.java,175,212
//,3
public class xxx {
    private void putTargets(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof PutTargetsRequest) {
                PutTargetsResponse result;
                try {
                    result = eventbridgeClient.putTargets((PutTargetsRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("PutTargets command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            PutTargetsRequest.Builder builder = PutTargetsRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME))) {
                String ruleName = exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME, String.class);
                builder.rule(ruleName);
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.TARGETS))) {
                Collection<Target> targets = exchange.getIn().getHeader(EventbridgeConstants.TARGETS, Collection.class);
                builder.targets(targets);
            } else {
                throw new IllegalArgumentException("At least one targets must be specified");
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            PutTargetsResponse result;
            try {
                result = eventbridgeClient.putTargets(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Put Targets command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};