//,temp,TestHSWebApp.java,124,130,temp,TestHSWebApp.java,67,72
//,2
public class xxx {
  @Test public void testJobCounterView() {
    LOG.info("JobCounterView");
    AppContext appContext = new MockAppContext(0, 1, 1, 1);
    Map<String, String> params = TestAMWebApp.getJobParams(appContext);
    WebAppTests.testPage(HsCountersPage.class, AppContext.class,
                         appContext, params);
  }

};