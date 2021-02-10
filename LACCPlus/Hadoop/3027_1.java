//,temp,TestDockerContainerRuntime.java,1449,1463,temp,TestDockerContainerRuntime.java,1295,1311
//,3
public class xxx {
  @Test
  public void testDefaultRWMountsInvalid() throws ContainerExecutionException {
    conf.setStrings(NM_DOCKER_DEFAULT_RW_MOUNTS,
        "source,target");
    DockerLinuxContainerRuntime runtime = new DockerLinuxContainerRuntime(
        mockExecutor, mockCGroupsHandler);
    runtime.initialize(conf, nmContext);

    try {
      runtime.launchContainer(builder.build());
      Assert.fail("Expected a launch container failure due to invalid mount.");
    } catch (ContainerExecutionException e) {
      LOG.info("Caught expected exception : " + e);
    }
  }

};