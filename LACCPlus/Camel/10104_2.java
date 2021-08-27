//,temp,StringTemplateEndpoint.java,105,161,temp,MvelEndpoint.java,92,151
//,3
public class xxx {
    @Override
    protected void onExchange(Exchange exchange) throws Exception {
        String path = getResourceUri();
        ObjectHelper.notNull(path, "resourceUri");

        if (allowTemplateFromHeader) {
            String newResourceUri = exchange.getIn().getHeader(MvelConstants.MVEL_RESOURCE_URI, String.class);
            if (newResourceUri != null) {
                exchange.getIn().removeHeader(MvelConstants.MVEL_RESOURCE_URI);

                log.debug("{} set to {} creating new endpoint to handle exchange", MvelConstants.MVEL_RESOURCE_URI,
                        newResourceUri);
                MvelEndpoint newEndpoint = findOrCreateEndpoint(getEndpointUri(), newResourceUri);
                newEndpoint.onExchange(exchange);
                return;
            }
        }

        CompiledTemplate compiled;
        ParserContext mvelContext = ParserContext.create();
        Map<String, Object> variableMap = ExchangeHelper.createVariableMap(exchange, isAllowContextMapAll());

        String content = null;
        if (allowTemplateFromHeader) {
            content = exchange.getIn().getHeader(MvelConstants.MVEL_TEMPLATE, String.class);
        }
        if (content != null) {
            // use content from header
            if (log.isDebugEnabled()) {
                log.debug("Mvel content read from header {} for endpoint {}", MvelConstants.MVEL_TEMPLATE, getEndpointUri());
            }
            // remove the header to avoid it being propagated in the routing
            exchange.getIn().removeHeader(MvelConstants.MVEL_TEMPLATE);
            compiled = TemplateCompiler.compileTemplate(content, mvelContext);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Mvel content read from resource {} with resourceUri: {} for endpoint {}", getResourceUri(), path,
                        getEndpointUri());
            }
            // getResourceAsInputStream also considers the content cache
            Reader reader = getEncoding() != null
                    ? new InputStreamReader(getResourceAsInputStream(), getEncoding())
                    : new InputStreamReader(getResourceAsInputStream());
            String template = IOHelper.toString(reader);
            if (!template.equals(this.template)) {
                this.template = template;
                this.compiled = TemplateCompiler.compileTemplate(template, mvelContext);
            }
            compiled = this.compiled;
        }

        // let mvel parse and execute the template
        log.debug("Mvel is evaluating using mvel context: {}", variableMap);
        Object result = TemplateRuntime.execute(compiled, mvelContext, variableMap);

        // now lets output the results to the exchange
        Message out = exchange.getOut();
        out.setBody(result.toString());
        out.setHeaders(exchange.getIn().getHeaders());
    }

};