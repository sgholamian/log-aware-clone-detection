//,temp,TestDockerContainerRuntime.java,1449,1463,temp,TestDockerContainerRuntime.java,1277,1293
//,3
public class xxx {
  @Test
  public void testUserMountInvalid() throws ContainerExecutionException {
    DockerLinuxContainerRuntime runtime = new DockerLinuxContainerRuntime(
        mockExecutor, mockCGroupsHandler);
    runtime.initialize(conf, nmContext);

    env.put(
        DockerLinuxContainerRuntime.ENV_DOCKER_CONTAINER_MOUNTS,
        "/source:target:ro,/source:target:other,/source:target:rw");

    try {
      runtime.launchContainer(builder.build());
      Assert.fail("Expected a launch container failure due to invalid mount.");
    } catch (ContainerExecutionException e) {
      LOG.info("Caught expected exception : " + e);
    }
  }

};