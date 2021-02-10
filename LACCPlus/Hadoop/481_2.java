//,temp,TestCGroupsHandlerImpl.java,160,230,temp,TestCGroupsHandlerImpl.java,125,158
//,3
public class xxx {
  @Test
  public void testCGroupPaths() {
    //As per junit behavior, we expect a new mock object to be available
    //in this test.
    verifyZeroInteractions(privilegedOperationExecutorMock);
    CGroupsHandler cGroupsHandler = null;
    try {
      cGroupsHandler = new CGroupsHandlerImpl(conf,
          privilegedOperationExecutorMock);
      cGroupsHandler.mountCGroupController(controller);
    } catch (ResourceHandlerException e) {
      LOG.error("Caught exception: " + e);
      Assert.assertTrue(
          "Unexpected ResourceHandlerException when mounting controller!",
          false);
    }

    String testCGroup = "container_01";
    String expectedPath = new StringBuffer(controllerPath).append('/')
        .append(testCGroup).toString();
    String path = cGroupsHandler.getPathForCGroup(controller, testCGroup);
    Assert.assertEquals(expectedPath, path);

    String expectedPathTasks = new StringBuffer(expectedPath).append('/')
        .append(CGroupsHandler.CGROUP_FILE_TASKS).toString();
    path = cGroupsHandler.getPathForCGroupTasks(controller, testCGroup);
    Assert.assertEquals(expectedPathTasks, path);

    String param = CGroupsHandler.CGROUP_PARAM_CLASSID;
    String expectedPathParam = new StringBuffer(expectedPath).append('/')
        .append(controller.getName()).append('.').append(param).toString();
    path = cGroupsHandler.getPathForCGroupParam(controller, testCGroup, param);
    Assert.assertEquals(expectedPathParam, path);
  }

};