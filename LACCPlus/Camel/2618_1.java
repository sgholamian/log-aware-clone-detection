//,temp,VelocityEndpoint.java,179,250,temp,FreemarkerEndpoint.java,127,187
//,3
public class xxx {
    @Override
    protected void onExchange(Exchange exchange) throws Exception {
        String path = getResourceUri();
        ObjectHelper.notNull(path, "resourceUri");

        if (allowTemplateFromHeader) {
            String newResourceUri = exchange.getIn().getHeader(VelocityConstants.VELOCITY_RESOURCE_URI, String.class);
            if (newResourceUri != null) {
                exchange.getIn().removeHeader(VelocityConstants.VELOCITY_RESOURCE_URI);

                log.debug("{} set to {} creating new endpoint to handle exchange", VelocityConstants.VELOCITY_RESOURCE_URI,
                        newResourceUri);
                VelocityEndpoint newEndpoint = findOrCreateEndpoint(getEndpointUri(), newResourceUri);
                newEndpoint.onExchange(exchange);
                return;
            }
        }

        Reader reader;
        String content = null;
        if (allowTemplateFromHeader) {
            content = exchange.getIn().getHeader(VelocityConstants.VELOCITY_TEMPLATE, String.class);
        }
        if (content != null) {
            // use content from header
            reader = new StringReader(content);
            if (log.isDebugEnabled()) {
                log.debug("Velocity content read from header {} for endpoint {}", VelocityConstants.VELOCITY_TEMPLATE,
                        getEndpointUri());
            }
            // remove the header to avoid it being propagated in the routing
            exchange.getIn().removeHeader(VelocityConstants.VELOCITY_TEMPLATE);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Velocity content read from resource {} with resourceUri: {} for endpoint {}", getResourceUri(), path,
                        getEndpointUri());
            }
            reader = getEncoding() != null
                    ? new InputStreamReader(getResourceAsInputStream(), getEncoding())
                    : new InputStreamReader(getResourceAsInputStream());
        }

        // getResourceAsInputStream also considers the content cache
        StringWriter buffer = new StringWriter();
        String logTag = getClass().getName();
        Context velocityContext = null;
        if (allowTemplateFromHeader) {
            velocityContext = exchange.getIn().getHeader(VelocityConstants.VELOCITY_CONTEXT, Context.class);
        }
        if (velocityContext == null) {
            Map<String, Object> variableMap = ExchangeHelper.createVariableMap(exchange, isAllowContextMapAll());
            if (allowTemplateFromHeader) {
                @SuppressWarnings("unchecked")
                Map<String, Object> supplementalMap
                        = exchange.getIn().getHeader(VelocityConstants.VELOCITY_SUPPLEMENTAL_CONTEXT, Map.class);
                if (supplementalMap != null) {
                    variableMap.putAll(supplementalMap);
                }
            }
            velocityContext = new VelocityContext(variableMap);
        }

        // let velocity parse and generate the result in buffer
        VelocityEngine engine = getVelocityEngine();
        log.debug("Velocity is evaluating using velocity context: {}", velocityContext);
        engine.evaluate(velocityContext, buffer, logTag, reader);

        // now lets output the results to the exchange
        Message out = exchange.getOut();
        out.setBody(buffer.toString());
        out.setHeaders(exchange.getIn().getHeaders());
    }

};