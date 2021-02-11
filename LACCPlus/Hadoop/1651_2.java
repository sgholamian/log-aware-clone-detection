//,temp,TestHSWebApp.java,132,138,temp,TestHSWebApp.java,124,130
//,3
public class xxx {
  @Test public void testJobCounterView() {
    LOG.info("JobCounterView");
    AppContext appContext = new MockAppContext(0, 1, 1, 1);
    Map<String, String> params = TestAMWebApp.getJobParams(appContext);
    WebAppTests.testPage(HsCountersPage.class, AppContext.class,
                         appContext, params);
  }

};