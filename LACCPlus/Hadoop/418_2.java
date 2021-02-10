//,temp,TestHSWebApp.java,215,236,temp,TestHSWebApp.java,158,178
//,3
public class xxx {
  @Test
  public void testLogsView2() throws IOException {
    LOG.info("HsLogsPage with data");
    MockAppContext ctx = new MockAppContext(0, 1, 1, 1);
    Map<String, String> params = new HashMap<String, String>();

    params.put(CONTAINER_ID, MRApp.newContainerId(1, 1, 333, 1)
        .toString());
    params.put(NM_NODENAME, 
        NodeId.newInstance(MockJobs.NM_HOST, MockJobs.NM_PORT).toString());
    params.put(ENTITY_STRING, "container_10_0001_01_000001");
    params.put(APP_OWNER, "owner");

    Injector injector =
        WebAppTests.testPage(AggregatedLogsPage.class, AppContext.class, ctx,
            params);
    PrintWriter spyPw = WebAppTests.getPrintWriter(injector);
    verify(spyPw).write(
        "Aggregation is not enabled. Try the nodemanager at "
            + MockJobs.NM_HOST + ":" + MockJobs.NM_PORT);
  }

};