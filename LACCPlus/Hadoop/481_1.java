//,temp,TestCGroupsHandlerImpl.java,160,230,temp,TestCGroupsHandlerImpl.java,125,158
//,3
public class xxx {
  @Test
  public void testCGroupOperations() {
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
    //Lets manually create a path to (partially) simulate a mounted controller
    //this is required because the handler uses a mocked privileged operation
    //executor
    new File(controllerPath).mkdirs();

    String testCGroup = "container_01";
    String expectedPath = new StringBuffer(controllerPath).append('/')
        .append(testCGroup).toString();
    try {
      String path = cGroupsHandler.createCGroup(controller, testCGroup);

      Assert.assertTrue(new File(expectedPath).exists());
      Assert.assertEquals(expectedPath, path);

      //update param and read param tests.
      //We don't use net_cls.classid because as a test param here because
      //cgroups provides very specific read/write semantics for classid (only
      //numbers can be written - potentially as hex but can be read out only
      //as decimal)
      String param = "test_param";
      String paramValue = "test_param_value";

      cGroupsHandler
          .updateCGroupParam(controller, testCGroup, param, paramValue);
      String paramPath = new StringBuffer(expectedPath).append('/')
          .append(controller.getName()).append('.').append(param).toString();
      File paramFile = new File(paramPath);

      Assert.assertTrue(paramFile.exists());
      try {
        Assert.assertEquals(paramValue, new String(Files.readAllBytes(
            paramFile.toPath())));
      } catch (IOException e) {
        LOG.error("Caught exception: " + e);
        Assert.fail("Unexpected IOException trying to read cgroup param!");
      }

      Assert.assertEquals(paramValue,
          cGroupsHandler.getCGroupParam(controller, testCGroup, param));

      //We can't really do a delete test here. Linux cgroups
      //implementation provides additional semantics - the cgroup cannot be
      //deleted if there are any tasks still running in the cgroup even if
      //the user attempting the delete has the file permissions to do so - we
      //cannot simulate that here. Even if we create a dummy 'tasks' file, we
      //wouldn't be able to simulate the delete behavior we need, since a cgroup
      //can be deleted using using 'rmdir' if the tasks file is empty. Such a
      //delete is not possible with a regular non-empty directory.
    } catch (ResourceHandlerException e) {
      LOG.error("Caught exception: " + e);
      Assert
        .fail("Unexpected ResourceHandlerException during cgroup operations!");
    }
  }

};