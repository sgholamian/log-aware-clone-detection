//,temp,TestHSWebApp.java,104,110,temp,TestHSWebApp.java,83,90
//,2
public class xxx {
  @Test public void testAttemptsView() {
    LOG.info("HsAttemptsPage");
    AppContext appContext = new MockAppContext(0, 1, 1, 1);
    Map<String, String> params = TestAMWebApp.getTaskParams(appContext);
    WebAppTests.testPage(HsAttemptsPage.class, AppContext.class,
                         appContext, params);
  }

};