//,temp,TestDockerContainerRuntime.java,1313,1329,temp,TestDockerContainerRuntime.java,918,936
//,3
public class xxx {
  @Test
  public void testLaunchPrivilegedContainersWithDisabledSetting()
      throws ContainerExecutionException {
    conf.setBoolean(YarnConfiguration.NM_DOCKER_ALLOW_PRIVILEGED_CONTAINERS,
        false);
    DockerLinuxContainerRuntime runtime = new DockerLinuxContainerRuntime(
        mockExecutor, mockCGroupsHandler);
    runtime.initialize(conf, nmContext);

    env.put(DockerLinuxContainerRuntime
            .ENV_DOCKER_CONTAINER_RUN_PRIVILEGED_CONTAINER, "true");

    try {
      runtime.launchContainer(builder.build());
      Assert.fail("Expected a privileged launch container failure.");
    } catch (ContainerExecutionException e) {
      LOG.info("Caught expected exception : " + e);
    }
  }

};