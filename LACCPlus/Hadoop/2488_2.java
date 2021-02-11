//,temp,NodeTimelineCollectorManager.java,271,322,temp,TimelineReaderServer.java,173,214
//,3
public class xxx {
  private void startTimelineReaderWebApp() {
    Configuration conf = getConfig();
    addFilters(conf);

    String hostProperty = YarnConfiguration.TIMELINE_SERVICE_READER_BIND_HOST;
    String host = conf.getTrimmed(hostProperty);
    if (host == null || host.isEmpty()) {
      // if reader bind-host is not set, fall back to timeline-service.bind-host
      // to maintain compatibility
      hostProperty = YarnConfiguration.TIMELINE_SERVICE_BIND_HOST;
    }
    String bindAddress = WebAppUtils
        .getWebAppBindURL(conf, hostProperty, webAppURLWithoutScheme);

    LOG.info("Instantiating TimelineReaderWebApp at " + bindAddress);
    try {

      String httpScheme = WebAppUtils.getHttpSchemePrefix(conf);

      HttpServer2.Builder builder = new HttpServer2.Builder()
            .setName("timeline")
            .setConf(conf)
            .addEndpoint(URI.create(httpScheme + bindAddress));

      if (httpScheme.equals(WebAppUtils.HTTPS_PREFIX)) {
        WebAppUtils.loadSslConfiguration(builder, conf);
      }
      readerWebServer = builder.build();
      readerWebServer.addJerseyResourcePackage(
          TimelineReaderWebServices.class.getPackage().getName() + ";"
              + GenericExceptionHandler.class.getPackage().getName() + ";"
              + YarnJacksonJaxbJsonProvider.class.getPackage().getName(),
          "/*");
      readerWebServer.setAttribute(TIMELINE_READER_MANAGER_ATTR,
          timelineReaderManager);
      readerWebServer.start();
    } catch (Exception e) {
      String msg = "TimelineReaderWebApp failed to start.";
      LOG.error(msg, e);
      throw new YarnRuntimeException(msg, e);
    }
  }

};