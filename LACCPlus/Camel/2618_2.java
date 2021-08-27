//,temp,VelocityEndpoint.java,179,250,temp,FreemarkerEndpoint.java,127,187
//,3
public class xxx {
    @Override
    protected void onExchange(Exchange exchange) throws Exception {
        String path = getResourceUri();
        ObjectHelper.notNull(configuration, "configuration");
        ObjectHelper.notNull(path, "resourceUri");

        if (allowTemplateFromHeader) {
            String newResourceUri = exchange.getIn().getHeader(FreemarkerConstants.FREEMARKER_RESOURCE_URI, String.class);
            if (newResourceUri != null) {
                exchange.getIn().removeHeader(FreemarkerConstants.FREEMARKER_RESOURCE_URI);

                log.debug("{} set to {} creating new endpoint to handle exchange", FreemarkerConstants.FREEMARKER_RESOURCE_URI,
                        newResourceUri);
                FreemarkerEndpoint newEndpoint = findOrCreateEndpoint(getEndpointUri(), newResourceUri);
                newEndpoint.onExchange(exchange);
                return;
            }
        }

        Reader reader = null;
        String content = null;
        if (allowTemplateFromHeader) {
            content = exchange.getIn().getHeader(FreemarkerConstants.FREEMARKER_TEMPLATE, String.class);
        }
        if (content != null) {
            // use content from header
            reader = new StringReader(content);
            // remove the header to avoid it being propagated in the routing
            exchange.getIn().removeHeader(FreemarkerConstants.FREEMARKER_TEMPLATE);
        }
        Object dataModel = null;
        if (allowTemplateFromHeader) {
            dataModel = exchange.getIn().getHeader(FreemarkerConstants.FREEMARKER_DATA_MODEL, Object.class);
        }
        if (dataModel == null) {
            dataModel = ExchangeHelper.createVariableMap(exchange, isAllowContextMapAll());
        }
        // let freemarker parse and generate the result in buffer
        Template template;

        if (reader != null) {
            log.debug("Freemarker is evaluating template read from header {} using context: {}",
                    FreemarkerConstants.FREEMARKER_TEMPLATE, dataModel);
            template = new Template("temp", reader, new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
        } else {
            log.debug("Freemarker is evaluating {} using context: {}", path, dataModel);
            if (getEncoding() != null) {
                template = configuration.getTemplate(path, getEncoding());
            } else {
                template = configuration.getTemplate(path);
            }
        }
        StringWriter buffer = new StringWriter();
        template.process(dataModel, buffer);
        buffer.flush();

        // now lets output the results to the exchange
        Message out = exchange.getOut();
        out.setBody(buffer.toString());
        out.setHeaders(exchange.getIn().getHeaders());
    }

};