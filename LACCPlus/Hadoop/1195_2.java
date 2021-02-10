//,temp,CrossOriginFilter.java,185,196,temp,CrossOriginFilter.java,174,183
//,3
public class xxx {
  private void initializeAllowedHeaders(FilterConfig filterConfig) {
    String allowedHeadersConfig =
        filterConfig.getInitParameter(ALLOWED_HEADERS);
    if (allowedHeadersConfig == null) {
      allowedHeadersConfig = ALLOWED_HEADERS_DEFAULT;
    }
    allowedHeaders.addAll(
        Arrays.asList(allowedHeadersConfig.trim().split("\\s*,\\s*")));
    LOG.info("Allowed Headers: " + getAllowedHeadersHeader());
  }

};