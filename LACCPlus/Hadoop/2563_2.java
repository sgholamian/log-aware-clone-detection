//,temp,TestHSWebApp.java,158,164,temp,TestHSWebApp.java,130,136
//,3
public class xxx {
  @Test public void testAttemptsView() {
    LOG.info("HsAttemptsPage");
    AppContext appContext = new MockAppContext(0, 1, 1, 1);
    Map<String, String> params = TestAMWebApp.getTaskParams(appContext);
    WebAppTests.testPage(HsAttemptsPage.class, AppContext.class,
                         appContext, params);
  }

};