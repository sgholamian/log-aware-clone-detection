//,temp,TestDockerContainerRuntime.java,1382,1396,temp,TestDockerContainerRuntime.java,1313,1329
//,3
public class xxx {
  @Test
  public void testDefaultROMountsInvalid() throws ContainerExecutionException {
    conf.setStrings(NM_DOCKER_DEFAULT_RO_MOUNTS,
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