//,temp,TestHSWebApp.java,83,90,temp,TestHSWebApp.java,74,81
//,2
public class xxx {
  @Test
  public void testTaskView() {
    LOG.info("HsTaskPage");
    AppContext appContext = new MockAppContext(0, 1, 1, 1);
    Map<String, String> params = TestAMWebApp.getTaskParams(appContext);
    WebAppTests
        .testPage(HsTaskPage.class, AppContext.class, appContext, params);
  }

};