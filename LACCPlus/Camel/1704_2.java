//,temp,EventbridgeProducer.java,286,317,temp,EventbridgeProducer.java,132,173
//,3
public class xxx {
    private void putRule(EventBridgeClient eventbridgeClient, Exchange exchange) throws InvalidPayloadException, IOException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof PutRuleRequest) {
                PutRuleResponse result;
                try {
                    result = eventbridgeClient.putRule((PutRuleRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("PutRule command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            PutRuleRequest.Builder builder = PutRuleRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME))) {
                String ruleName = exchange.getIn().getHeader(EventbridgeConstants.RULE_NAME, String.class);
                builder.name(ruleName);
            }
            if (ObjectHelper.isEmpty(exchange.getIn().getHeader(EventbridgeConstants.EVENT_PATTERN))) {
                try (InputStream s = ResourceHelper.resolveMandatoryResourceAsInputStream(this.getEndpoint().getCamelContext(),
                        getConfiguration().getEventPatternFile())) {
                    String eventPattern = IOUtils.toString(s, Charset.defaultCharset());
                    builder.eventPattern(eventPattern);
                }
            } else {
                String eventPattern = exchange.getIn().getHeader(EventbridgeConstants.EVENT_PATTERN, String.class);
                builder.eventPattern(eventPattern);
            }
            builder.eventBusName(getConfiguration().getEventbusName());
            PutRuleResponse result;
            try {
                result = eventbridgeClient.putRule(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Put Rule command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};