//,temp,TestDockerContainerRuntime.java,1313,1329,temp,TestDockerContainerRuntime.java,918,936
//,3
public class xxx {
  @Test
  public void testUserMountModeNulInvalid() throws ContainerExecutionException {
    DockerLinuxContainerRuntime runtime = new DockerLinuxContainerRuntime(
        mockExecutor, mockCGroupsHandler);
    runtime.initialize(conf, nmContext);

    env.put(
        DockerLinuxContainerRuntime.ENV_DOCKER_CONTAINER_MOUNTS,
        "/s\0ource:target:ro");

    try {
      runtime.launchContainer(builder.build());
      Assert.fail("Expected a launch container failure due to NUL in mount.");
    } catch (ContainerExecutionException e) {
      LOG.info("Caught expected exception : " + e);
    }
  }

};