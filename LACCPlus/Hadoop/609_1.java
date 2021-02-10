//,temp,TestHSWebApp.java,132,138,temp,TestHSWebApp.java,67,72
//,3
public class xxx {
  @Test public void testJobCounterViewForKilledJob() {
    LOG.info("JobCounterViewForKilledJob");
    AppContext appContext = new MockAppContext(0, 1, 1, 1, true);
    Map<String, String> params = TestAMWebApp.getJobParams(appContext);
    WebAppTests.testPage(HsCountersPage.class, AppContext.class,
        appContext, params);
  }

};