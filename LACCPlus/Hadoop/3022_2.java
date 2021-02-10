//,temp,TestDockerContainerRuntime.java,1382,1396,temp,TestDockerContainerRuntime.java,815,831
//,3
public class xxx {
  @Test
  public void testLaunchPidNamespaceContainersWithDisabledSetting()
      throws ContainerExecutionException {
    DockerLinuxContainerRuntime runtime = new DockerLinuxContainerRuntime(
        mockExecutor, mockCGroupsHandler);
    runtime.initialize(conf, nmContext);

    env.put(DockerLinuxContainerRuntime
        .ENV_DOCKER_CONTAINER_PID_NAMESPACE, "host");

    try {
      runtime.launchContainer(builder.build());
      Assert.fail("Expected a pid host disabled container failure.");
    } catch (ContainerExecutionException e) {
      LOG.info("Caught expected exception : " + e);
    }
  }

};