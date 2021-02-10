//,temp,TestHSWebApp.java,97,104,temp,TestHSWebApp.java,69,74
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