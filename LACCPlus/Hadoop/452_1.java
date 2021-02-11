//,temp,TestWebAppProxyServlet.java,364,396,temp,WebAppProxy.java,91,117
//,3
public class xxx {
    @Override
    protected void serviceStart() throws Exception {
      Configuration conf = getConfig();
      String bindAddress = conf.get(YarnConfiguration.PROXY_ADDRESS);
      bindAddress = StringUtils.split(bindAddress, ':')[0];
      AccessControlList acl = new AccessControlList(
          conf.get(YarnConfiguration.YARN_ADMIN_ACL, 
          YarnConfiguration.DEFAULT_YARN_ADMIN_ACL));
      proxyServer = new HttpServer2.Builder()
          .setName("proxy")
          .addEndpoint(
              URI.create(WebAppUtils.getHttpSchemePrefix(conf) + bindAddress
                  + ":0")).setFindPort(true)
          .setConf(conf)
          .setACL(acl)
          .build();
      proxyServer.addServlet(ProxyUriUtils.PROXY_SERVLET_NAME,
          ProxyUriUtils.PROXY_PATH_SPEC, WebAppProxyServlet.class);

      appReportFetcher = new AppReportFetcherForTest(conf);
      proxyServer.setAttribute(FETCHER_ATTRIBUTE,
          appReportFetcher );
      proxyServer.setAttribute(IS_SECURITY_ENABLED_ATTRIBUTE, Boolean.TRUE);
      
      String proxy = WebAppUtils.getProxyHostAndPort(conf);
      String[] proxyParts = proxy.split(":");
      String proxyHost = proxyParts[0];
      
      proxyServer.setAttribute(PROXY_HOST_ATTRIBUTE, proxyHost);
      proxyServer.start();
      LOG.info("Proxy server is started at port {}",
          proxyServer.getConnectorAddress(0).getPort());
    }

};