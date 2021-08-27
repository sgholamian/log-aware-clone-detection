//,temp,StringTemplateEndpoint.java,105,161,temp,MvelEndpoint.java,92,151
//,3
public class xxx {
    @Override
    protected void onExchange(Exchange exchange) throws Exception {
        String template = null;
        String path = getResourceUri();
        ObjectHelper.notNull(path, "resourceUri");

        StringWriter buffer = new StringWriter();

        Map<String, Object> variableMap = null;

        if (allowTemplateFromHeader) {
            String newResourceUri
                    = exchange.getIn().getHeader(StringTemplateConstants.STRINGTEMPLATE_RESOURCE_URI, String.class);
            if (newResourceUri != null) {
                exchange.getIn().removeHeader(StringTemplateConstants.STRINGTEMPLATE_RESOURCE_URI);

                log.debug("{} set to {} creating new endpoint to handle exchange",
                        StringTemplateConstants.STRINGTEMPLATE_RESOURCE_URI,
                        newResourceUri);
                StringTemplateEndpoint newEndpoint = findOrCreateEndpoint(getEndpointUri(), newResourceUri);
                newEndpoint.onExchange(exchange);
                return;
            }
            variableMap = exchange.getIn().getHeader(StringTemplateConstants.STRINGTEMPLATE_VARIABLE_MAP, Map.class);
            template = exchange.getIn().getHeader(StringTemplateConstants.STRINGTEMPLATE_TEMPLATE, String.class);
        }

        if (variableMap == null) {
            variableMap = ExchangeHelper.createVariableMap(exchange, isAllowContextMapAll());
        }

        if (template != null) {
            log.debug("StringTemplate content read from header {} for endpoint {}",
                    StringTemplateConstants.STRINGTEMPLATE_TEMPLATE,
                    getEndpointUri());
            // remove the header to avoid it being propagated in the routing
            exchange.getIn().removeHeader(StringTemplateConstants.STRINGTEMPLATE_TEMPLATE);
        } else {
            log.debug("StringTemplate content read from resource {} with resourceUri: {} for endpoint {}", getResourceUri(),
                    path,
                    getEndpointUri());
            template = exchange.getContext().getTypeConverter().mandatoryConvertTo(String.class, getResourceAsInputStream());
        }
        // getResourceAsInputStream also considers the content cache
        ST stTemplate = new ST(template, delimiterStart, delimiterStop);
        for (Map.Entry<String, Object> entry : variableMap.entrySet()) {
            stTemplate.add(entry.getKey(), entry.getValue());
        }
        log.debug("StringTemplate is writing using attributes: {}", variableMap);
        stTemplate.write(new NoIndentWriter(buffer));

        // now lets output the results to the exchange
        Message out = exchange.getOut();
        out.setBody(buffer.toString());
        out.setHeaders(exchange.getIn().getHeaders());
        out.setHeader(StringTemplateConstants.STRINGTEMPLATE_RESOURCE_URI, getResourceUri());
    }

};