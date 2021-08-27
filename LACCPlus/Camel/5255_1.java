//,temp,RestOpenApiProcessor.java,55,129,temp,RestSwaggerProcessor.java,56,127
//,3
public class xxx {
    @Override
    public void process(Exchange exchange) throws Exception {

        String contextId = exchange.getContext().getName();
        String route = exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class);
        String accept = exchange.getIn().getHeader("Accept", String.class);

        RestApiResponseAdapter adapter = new ExchangeRestApiResponseAdapter(exchange);

        // whether to use json or yaml
        boolean json = false;
        boolean yaml = false;
        if (route != null && route.endsWith("/openapi.json")) {
            json = true;
            route = route.substring(0, route.length() - 13);
        } else if (route != null && route.endsWith("/openapi.yaml")) {
            yaml = true;
            route = route.substring(0, route.length() - 13);
        }
        if (accept != null && !json && !yaml) {
            json = accept.toLowerCase(Locale.US).contains("json");
            yaml = accept.toLowerCase(Locale.US).contains("yaml");
        }
        if (!json && !yaml) {
            // json is default
            json = true;
        }

        try {
            // render list of camel contexts as root
            if (contextIdListing && (ObjectHelper.isEmpty(route) || route.equals("/"))) {
                support.renderCamelContexts(exchange.getContext(), adapter, contextId, contextIdPattern, json, yaml,
                        configuration);
            } else {
                String name;
                if (contextIdListing && ObjectHelper.isNotEmpty(route)) {
                    // first part is the camel context
                    if (route.startsWith("/")) {
                        route = route.substring(1);
                    }
                    // the remainder is the route part
                    name = route.split("/")[0];
                    if (route.startsWith(contextId)) {
                        route = route.substring(name.length());
                    }
                } else {
                    // listing not enabled then get current camel context as the name
                    name = exchange.getContext().getName();
                    // prevent route filtering
                    route = "";
                }

                boolean match = true;
                if (contextIdPattern != null) {
                    if ("#name#".equals(contextIdPattern)) {
                        match = name.equals(contextId);
                    } else {
                        match = PatternHelper.matchPattern(name, contextIdPattern);
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Match contextId: {} with pattern: {} -> {}", name, contextIdPattern, match);
                    }
                }

                if (!match) {
                    adapter.noContent();
                } else {
                    support.renderResourceListing(exchange.getContext(), adapter, openApiConfig, name, route, json, yaml,
                            exchange.getIn().getHeaders(), exchange.getContext().getClassResolver(), configuration);
                }
            }
        } catch (Exception e) {
            LOG.warn("Error rendering OpenApi API due {}", e.getMessage(), e);
        }
    }

};