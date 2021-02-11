//,temp,TestDockerContainerRuntime.java,1295,1311,temp,TestDockerContainerRuntime.java,1277,1293
//,2
public class xxx {
  @Test
  public void testUserMountModeInvalid() throws ContainerExecutionException {
    DockerLinuxContainerRuntime runtime = new DockerLinuxContainerRuntime(
        mockExecutor, mockCGroupsHandler);
    runtime.initialize(conf, nmContext);

    env.put(
        DockerLinuxContainerRuntime.ENV_DOCKER_CONTAINER_MOUNTS,
        "/source:target:other");

    try {
      runtime.launchContainer(builder.build());
      Assert.fail("Expected a launch container failure due to invalid mode.");
    } catch (ContainerExecutionException e) {
      LOG.info("Caught expected exception : " + e);
    }
  }

};