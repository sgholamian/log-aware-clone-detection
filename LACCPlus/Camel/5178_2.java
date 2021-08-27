//,temp,RestSwaggerSupport.java,293,350,temp,RestOpenApiSupport.java,461,519
//,3
public class xxx {
    public void renderCamelContexts(
            CamelContext camelContext, RestApiResponseAdapter response, String contextId,
            String contextIdPattern, boolean json, boolean yaml,
            RestConfiguration configuration)
            throws Exception {
        LOG.trace("renderCamelContexts");

        if (cors) {
            setupCorsHeaders(response, configuration.getCorsHeaders());
        }

        List<String> contexts = findCamelContexts(camelContext);

        // filter non matched CamelContext's
        if (contextIdPattern != null) {
            Iterator<String> it = contexts.iterator();
            while (it.hasNext()) {
                String name = it.next();

                boolean match;
                if ("#name#".equals(contextIdPattern)) {
                    match = name.equals(contextId);
                } else {
                    match = PatternHelper.matchPattern(name, contextIdPattern);
                }
                if (!match) {
                    it.remove();
                }
            }
        }

        StringBuffer sb = new StringBuffer();

        if (json) {
            response.setHeader(Exchange.CONTENT_TYPE, "application/json");

            sb.append("[\n");
            for (int i = 0; i < contexts.size(); i++) {
                String name = contexts.get(i);
                sb.append("{\"name\": \"").append(name).append("\"}");
                if (i < contexts.size() - 1) {
                    sb.append(",\n");
                }
            }
            sb.append("\n]");
        } else {
            response.setHeader(Exchange.CONTENT_TYPE, "text/yaml");

            for (int i = 0; i < contexts.size(); i++) {
                String name = contexts.get(i);
                sb.append("- \"").append(name).append("\"\n");
            }
        }

        int len = sb.length();
        response.setHeader(Exchange.CONTENT_LENGTH, "" + len);

        response.writeBytes(sb.toString().getBytes());
    }

};