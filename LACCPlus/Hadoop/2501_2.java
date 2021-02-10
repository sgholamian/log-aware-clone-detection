//,temp,TestContainerManager.java,838,926,temp,TestContainerManager.java,641,759
//,3
public class xxx {
  @Test
  public void testContainerUpgradeSuccessExplicitRollback() throws IOException,
      InterruptedException, YarnException {
    Listener listener = new Listener();
    ((NodeManager.DefaultContainerStateListener)containerManager.context.
        getContainerStateTransitionListener()).addListener(listener);
    String[] pids = testContainerReInitSuccess(false);

    // Test that the container can be Restarted after the successful upgrrade.
    // Also, since there is a rollback context present before the restart, it
    // should be possible to rollback the container AFTER the restart.
    pids[1] = doRestartTests(createContainerId(0),
        new File(tmpDir, "start_file_n.txt").getAbsoluteFile(),
        "Upgrade World!", pids[1], true);

    // Delete the old start File..
    File oldStartFile = new File(tmpDir, "start_file_o.txt").getAbsoluteFile();

    oldStartFile.delete();

    ContainerId cId = createContainerId(0);
    // Explicit Rollback
    containerManager.rollbackLastReInitialization(cId);

    Container container =
        containerManager.getContext().getContainers().get(cId);
    Assert.assertTrue(container.isReInitializing());
    // Original should be dead anyway
    Assert.assertFalse("Original Process is still alive!",
        DefaultContainerExecutor.containerIsAlive(pids[0]));

    // Wait for new container to startup
    int timeoutSecs = 0;
    while (container.isReInitializing() && timeoutSecs++ < 20) {
      Thread.sleep(1000);
      LOG.info("Waiting for ReInitialization to complete..");
    }
    Assert.assertFalse(container.isReInitializing());

    timeoutSecs = 0;
    // Wait for new processStartfile to be created
    while (!oldStartFile.exists() && timeoutSecs++ < 20) {
      Thread.sleep(1000);
      LOG.info("Waiting for New process start-file to be created");
    }

    // Now verify the contents of the file
    BufferedReader reader =
        new BufferedReader(new FileReader(oldStartFile));
    Assert.assertEquals("Hello World!", reader.readLine());
    // Get the pid of the process
    String rolledBackPid = reader.readLine().trim();
    // No more lines
    Assert.assertEquals(null, reader.readLine());

    Assert.assertNotEquals("The Rolled-back process should be a different pid",
        pids[0], rolledBackPid);

    List<org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
        ContainerState> containerStates =
        listener.states.get(createContainerId(0));
    Assert.assertEquals(Arrays.asList(
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.NEW,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.LOCALIZING,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.SCHEDULED,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.RUNNING,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.REINITIALIZING,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.REINITIALIZING_AWAITING_KILL,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.REINITIALIZING_AWAITING_KILL,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.SCHEDULED,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.RUNNING,
        // This is the successful restart
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.REINITIALIZING_AWAITING_KILL,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.REINITIALIZING_AWAITING_KILL,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.SCHEDULED,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.RUNNING,
        // This is the rollback
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.REINITIALIZING_AWAITING_KILL,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.REINITIALIZING_AWAITING_KILL,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.SCHEDULED,
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.RUNNING), containerStates);

    List<ContainerEventType> containerEventTypes =
        listener.events.get(createContainerId(0));
    Assert.assertEquals(Arrays.asList(
        ContainerEventType.INIT_CONTAINER,
        ContainerEventType.RESOURCE_LOCALIZED,
        ContainerEventType.CONTAINER_LAUNCHED,
        ContainerEventType.REINITIALIZE_CONTAINER,
        ContainerEventType.RESOURCE_LOCALIZED,
        ContainerEventType.UPDATE_DIAGNOSTICS_MSG,
        ContainerEventType.CONTAINER_KILLED_ON_REQUEST,
        ContainerEventType.CONTAINER_LAUNCHED,
        ContainerEventType.REINITIALIZE_CONTAINER,
        ContainerEventType.UPDATE_DIAGNOSTICS_MSG,
        ContainerEventType.CONTAINER_KILLED_ON_REQUEST,
        ContainerEventType.CONTAINER_LAUNCHED,
        ContainerEventType.ROLLBACK_REINIT,
        ContainerEventType.UPDATE_DIAGNOSTICS_MSG,
        ContainerEventType.CONTAINER_KILLED_ON_REQUEST,
        ContainerEventType.CONTAINER_LAUNCHED), containerEventTypes);
  }

};