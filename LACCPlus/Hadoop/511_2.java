//,temp,TestHSWebApp.java,104,110,temp,TestHSWebApp.java,74,81
//,2
public class xxx {
  @Test
  public void testTasksView() {
    LOG.info("HsTasksPage");
    AppContext appContext = new MockAppContext(0, 1, 1, 1);
    Map<String, String> params = TestAMWebApp.getTaskParams(appContext);
    WebAppTests.testPage(HsTasksPage.class, AppContext.class, appContext,
        params);
  }

};