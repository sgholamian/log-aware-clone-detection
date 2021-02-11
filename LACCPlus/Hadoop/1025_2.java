//,temp,TestHSWebApp.java,215,236,temp,TestHSWebApp.java,180,213
//,3
public class xxx {
  @Test
  public void testLogsViewSingle() throws IOException {
    LOG.info("HsLogsPage with params for single log and data limits");
    MockAppContext ctx = new MockAppContext(0, 1, 1, 1);
    Map<String, String> params = new HashMap<String, String>();

    final Configuration conf = new YarnConfiguration();
    conf.setBoolean(YarnConfiguration.LOG_AGGREGATION_ENABLED, true);

    params.put("start", "-2048");
    params.put("end", "-1024");
    params.put(CONTAINER_LOG_TYPE, "syslog");
    params.put(CONTAINER_ID, MRApp.newContainerId(1, 1, 333, 1)
        .toString());
    params.put(NM_NODENAME,
        NodeId.newInstance(MockJobs.NM_HOST, MockJobs.NM_PORT).toString());
    params.put(ENTITY_STRING, "container_10_0001_01_000001");
    params.put(APP_OWNER, "owner");

    Injector injector =
        WebAppTests.testPage(AggregatedLogsPage.class, AppContext.class, ctx,
            params, new AbstractModule() {
          @Override
          protected void configure() {
            bind(Configuration.class).toInstance(conf);
          }
        });
    PrintWriter spyPw = WebAppTests.getPrintWriter(injector);
    verify(spyPw).write(
        "Logs not available for container_10_0001_01_000001."
            + " Aggregation may not be complete, "
            + "Check back later or try the nodemanager at "
            + MockJobs.NM_HOST + ":" + MockJobs.NM_PORT);
  }

};