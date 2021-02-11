//,temp,TestHSWebApp.java,241,262,temp,TestHSWebApp.java,184,204
//,3
public class xxx {
  @Test
  public void testLogsViewBadStartEnd() throws IOException {
    LOG.info("HsLogsPage with bad start/end params");
    MockAppContext ctx = new MockAppContext(0, 1, 1, 1);
    Map<String, String> params = new HashMap<String, String>();

    params.put("start", "foo");
    params.put("end", "bar");
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
    verify(spyPw).write("Invalid log start value: foo");
    verify(spyPw).write("Invalid log end value: bar");
  }

};