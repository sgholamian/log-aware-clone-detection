//,temp,TestDockerContainerRuntime.java,1095,1111,temp,TestDockerContainerRuntime.java,938,962
//,3
public class xxx {
  @Test
  public void testLaunchPrivilegedContainersWithEnabledSettingAndDefaultACL()
      throws ContainerExecutionException {
    //Enable privileged containers.
    conf.setBoolean(YarnConfiguration.NM_DOCKER_ALLOW_PRIVILEGED_CONTAINERS,
        true);
    conf.set(YarnConfiguration.NM_DOCKER_PRIVILEGED_CONTAINERS_ACL, "");

    DockerLinuxContainerRuntime runtime = new DockerLinuxContainerRuntime(
        mockExecutor, mockCGroupsHandler);
    runtime.initialize(conf, nmContext);

    env.put(DockerLinuxContainerRuntime
            .ENV_DOCKER_CONTAINER_RUN_PRIVILEGED_CONTAINER, "true");
    //By default
    // yarn.nodemanager.runtime.linux.docker.privileged-containers.acl
    // is empty. So we expect this launch to fail.

    try {
      runtime.launchContainer(builder.build());
      Assert.fail("Expected a privileged launch container failure.");
    } catch (ContainerExecutionException e) {
      LOG.info("Caught expected exception : " + e);
    }
  }

};