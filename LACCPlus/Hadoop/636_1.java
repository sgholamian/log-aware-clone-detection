//,temp,CrossOriginFilter.java,185,196,temp,CrossOriginFilter.java,163,172
//,3
public class xxx {
  private void initializeAllowedOrigins(FilterConfig filterConfig) {
    String allowedOriginsConfig =
        filterConfig.getInitParameter(ALLOWED_ORIGINS);
    if (allowedOriginsConfig == null) {
      allowedOriginsConfig = ALLOWED_ORIGINS_DEFAULT;
    }
    allowedOrigins.addAll(
        Arrays.asList(allowedOriginsConfig.trim().split("\\s*,\\s*")));
    allowAllOrigins = allowedOrigins.contains("*");
    LOG.info("Allowed Origins: " + StringUtils.join(allowedOrigins, ','));
    LOG.info("Allow All Origins: " + allowAllOrigins);
  }

};