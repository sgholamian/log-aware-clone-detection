//,temp,CrossOriginFilter.java,174,183,temp,CrossOriginFilter.java,163,172
//,2
public class xxx {
  private void initializeAllowedMethods(FilterConfig filterConfig) {
    String allowedMethodsConfig =
        filterConfig.getInitParameter(ALLOWED_METHODS);
    if (allowedMethodsConfig == null) {
      allowedMethodsConfig = ALLOWED_METHODS_DEFAULT;
    }
    allowedMethods.addAll(
        Arrays.asList(allowedMethodsConfig.trim().split("\\s*,\\s*")));
    LOG.info("Allowed Methods: " + getAllowedMethodsHeader());
  }

};