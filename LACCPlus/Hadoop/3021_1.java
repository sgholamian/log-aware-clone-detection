//,temp,TestDockerContainerRuntime.java,1095,1111,temp,TestDockerContainerRuntime.java,918,936
//,3
public class xxx {
  @Test
  public void testMountSourceOnly() throws ContainerExecutionException {
    DockerLinuxContainerRuntime runtime = new DockerLinuxContainerRuntime(
        mockExecutor, mockCGroupsHandler);
    runtime.initialize(conf, nmContext);

    env.put(
        DockerLinuxContainerRuntime.ENV_DOCKER_CONTAINER_MOUNTS,
        "/source");

    try {
      runtime.launchContainer(builder.build());
      Assert.fail("Expected a launch container failure due to invalid mount.");
    } catch (ContainerExecutionException e) {
      LOG.info("Caught expected exception : " + e);
    }
  }

};