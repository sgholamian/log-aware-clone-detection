//,temp,TestContainerManager.java,838,926,temp,TestContainerManager.java,641,759
//,3
public class xxx {
  @Test
  public void testContainerUpgradeRollbackDueToFailure() throws IOException,
      InterruptedException, YarnException {
    if (Shell.WINDOWS) {
      return;
    }
    containerManager.start();
    Listener listener = new Listener();
    ((NodeManager.DefaultContainerStateListener)containerManager.context.
        getContainerStateTransitionListener()).addListener(listener);
    // ////// Construct the Container-id
    ContainerId cId = createContainerId(0);
    File oldStartFile = new File(tmpDir, "start_file_o.txt").getAbsoluteFile();

    String pid = prepareInitialContainer(cId, oldStartFile);

    File newStartFile = new File(tmpDir, "start_file_n.txt").getAbsoluteFile();

    prepareContainerUpgrade(false, true, false, cId, newStartFile);

    // Assert that the First process is not alive anymore
    Assert.assertFalse("Original Process is still alive!",
        DefaultContainerExecutor.containerIsAlive(pid));

    int timeoutSecs = 0;
    // Wait for oldStartFile to be created
    while (!oldStartFile.exists() && timeoutSecs++ < 20) {
      System.out.println("\nFiles: " +
          Arrays.toString(oldStartFile.getParentFile().list()));
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
        pid, rolledBackPid);

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
        org.apache.hadoop.yarn.server.nodemanager.containermanager.container.
            ContainerState.RUNNING,
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
        ContainerEventType.UPDATE_DIAGNOSTICS_MSG,
        ContainerEventType.CONTAINER_EXITED_WITH_FAILURE,
        ContainerEventType.CONTAINER_LAUNCHED), containerEventTypes);
  }

};