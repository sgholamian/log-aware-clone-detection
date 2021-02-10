//,temp,TestWebAppProxyServlet.java,364,396,temp,WebAppProxy.java,91,117
//,3
public class xxx {
  @Override
  protected void serviceStart() throws Exception {
    try {
      Configuration conf = getConfig();
      HttpServer2.Builder b = new HttpServer2.Builder()
          .setName("proxy")
          .addEndpoint(
              URI.create(WebAppUtils.getHttpSchemePrefix(conf) + bindAddress
                  + ":" + port)).setFindPort(port == 0).setConf(getConfig())
          .setACL(acl);
      if (YarnConfiguration.useHttps(conf)) {
        WebAppUtils.loadSslConfiguration(b);
      }
      proxyServer = b.build();
      proxyServer.addServlet(ProxyUriUtils.PROXY_SERVLET_NAME,
          ProxyUriUtils.PROXY_PATH_SPEC, WebAppProxyServlet.class);
      proxyServer.setAttribute(FETCHER_ATTRIBUTE, fetcher);
      proxyServer
          .setAttribute(IS_SECURITY_ENABLED_ATTRIBUTE, isSecurityEnabled);
      proxyServer.setAttribute(PROXY_HOST_ATTRIBUTE, proxyHost);
      proxyServer.start();
    } catch (IOException e) {
      LOG.error("Could not start proxy web server",e);
      throw e;
    }
    super.serviceStart();
  }

};