//,temp,NodeTimelineCollectorManager.java,271,322,temp,TimelineReaderServer.java,173,214
//,3
public class xxx {
  private void startWebApp() {
    Configuration conf = getConfig();
    String initializers = conf.get("hadoop.http.filter.initializers", "");
    Set<String> defaultInitializers = new LinkedHashSet<String>();
    TimelineServerUtils.addTimelineAuthFilter(
        initializers, defaultInitializers, tokenMgrService);
    TimelineServerUtils.setTimelineFilters(
        conf, initializers, defaultInitializers);

    String bindAddress = null;
    String host =
        conf.getTrimmed(YarnConfiguration.TIMELINE_SERVICE_COLLECTOR_BIND_HOST);
    if (host == null || host.isEmpty()) {
      // if collector bind-host is not set, fall back to
      // timeline-service.bind-host to maintain compatibility
      bindAddress =
          conf.get(YarnConfiguration.DEFAULT_TIMELINE_SERVICE_BIND_HOST,
              YarnConfiguration.DEFAULT_TIMELINE_SERVICE_BIND_HOST) + ":0";
    } else {
      bindAddress = host + ":0";
    }

    try {
      HttpServer2.Builder builder = new HttpServer2.Builder()
          .setName("timeline")
          .setConf(conf)
          .addEndpoint(URI.create(
              (YarnConfiguration.useHttps(conf) ? "https://" : "http://") +
                  bindAddress));
      if (YarnConfiguration.useHttps(conf)) {
        builder = WebAppUtils.loadSslConfiguration(builder, conf);
      }
      timelineRestServer = builder.build();

      timelineRestServer.addJerseyResourcePackage(
          TimelineCollectorWebService.class.getPackage().getName() + ";"
              + GenericExceptionHandler.class.getPackage().getName() + ";"
              + YarnJacksonJaxbJsonProvider.class.getPackage().getName(),
          "/*");
      timelineRestServer.setAttribute(COLLECTOR_MANAGER_ATTR_KEY, this);
      timelineRestServer.start();
    } catch (Exception e) {
      String msg = "The per-node collector webapp failed to start.";
      LOG.error(msg, e);
      throw new YarnRuntimeException(msg, e);
    }
    //TODO: We need to think of the case of multiple interfaces
    this.timelineRestServerBindAddress = WebAppUtils.getResolvedAddress(
        timelineRestServer.getConnectorAddress(0));
    LOG.info("Instantiated the per-node collector webapp at " +
        timelineRestServerBindAddress);
  }

};