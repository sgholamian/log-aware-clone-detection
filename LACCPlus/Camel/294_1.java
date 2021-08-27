//,temp,RestSwaggerSupport.java,352,383,temp,RestOpenApiSupport.java,77,108
//,2
public class xxx {
    private static void setupCorsHeaders(RestApiResponseAdapter response, Map<String, String> corsHeaders) {
        // use default value if none has been configured
        String allowOrigin = corsHeaders != null ? corsHeaders.get("Access-Control-Allow-Origin") : null;
        if (allowOrigin == null) {
            allowOrigin = RestConfiguration.CORS_ACCESS_CONTROL_ALLOW_ORIGIN;
        }
        String allowMethods = corsHeaders != null ? corsHeaders.get("Access-Control-Allow-Methods") : null;
        if (allowMethods == null) {
            allowMethods = RestConfiguration.CORS_ACCESS_CONTROL_ALLOW_METHODS;
        }
        String allowHeaders = corsHeaders != null ? corsHeaders.get("Access-Control-Allow-Headers") : null;
        if (allowHeaders == null) {
            allowHeaders = RestConfiguration.CORS_ACCESS_CONTROL_ALLOW_HEADERS;
        }
        String maxAge = corsHeaders != null ? corsHeaders.get("Access-Control-Max-Age") : null;
        if (maxAge == null) {
            maxAge = RestConfiguration.CORS_ACCESS_CONTROL_MAX_AGE;
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Using CORS headers[");
            LOG.trace("  Access-Control-Allow-Origin={}", allowOrigin);
            LOG.trace("  Access-Control-Allow-Methods={}", allowMethods);
            LOG.trace("  Access-Control-Allow-Headers={}", allowHeaders);
            LOG.trace("  Access-Control-Max-Age={}", maxAge);
            LOG.trace("]");
        }
        response.setHeader("Access-Control-Allow-Origin", allowOrigin);
        response.setHeader("Access-Control-Allow-Methods", allowMethods);
        response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        response.setHeader("Access-Control-Max-Age", maxAge);
    }

};