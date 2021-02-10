//,temp,TestContainersMonitor.java,242,354,temp,TestContainerManager.java,357,457
//,3
public class xxx {
  @Test
  public void testContainerKillOnMemoryOverflow() throws IOException,
      InterruptedException, YarnException {

    if (!ProcfsBasedProcessTree.isAvailable()) {
      return;
    }

    containerManager.start();

    File scriptFile = new File(tmpDir, "scriptFile.sh");
    PrintWriter fileWriter = new PrintWriter(scriptFile);
    File processStartFile =
        new File(tmpDir, "start_file.txt").getAbsoluteFile();
    fileWriter.write("\numask 0"); // So that start file is readable by the
                                   // test.
    fileWriter.write("\necho Hello World! > " + processStartFile);
    fileWriter.write("\necho $$ >> " + processStartFile);
    fileWriter.write("\nsleep 15");
    fileWriter.close();

    ContainerLaunchContext containerLaunchContext =
        recordFactory.newRecordInstance(ContainerLaunchContext.class);
    // ////// Construct the Container-id
    ApplicationId appId = ApplicationId.newInstance(0, 0);
    ApplicationAttemptId appAttemptId = ApplicationAttemptId.newInstance(appId, 1);
    ContainerId cId = ContainerId.newContainerId(appAttemptId, 0);

    URL resource_alpha =
        URL.fromPath(localFS
            .makeQualified(new Path(scriptFile.getAbsolutePath())));
    LocalResource rsrc_alpha =
        recordFactory.newRecordInstance(LocalResource.class);
    rsrc_alpha.setResource(resource_alpha);
    rsrc_alpha.setSize(-1);
    rsrc_alpha.setVisibility(LocalResourceVisibility.APPLICATION);
    rsrc_alpha.setType(LocalResourceType.FILE);
    rsrc_alpha.setTimestamp(scriptFile.lastModified());
    String destinationFile = "dest_file";
    Map<String, LocalResource> localResources =
        new HashMap<String, LocalResource>();
    localResources.put(destinationFile, rsrc_alpha);
    containerLaunchContext.setLocalResources(localResources);
    List<String> commands = new ArrayList<String>();
    commands.add("/bin/bash");
    commands.add(scriptFile.getAbsolutePath());
    containerLaunchContext.setCommands(commands);
    Resource r = BuilderUtils.newResource(0, 0);
    ContainerTokenIdentifier containerIdentifier =
        new ContainerTokenIdentifier(cId, context.getNodeId().toString(), user,
          r, System.currentTimeMillis() + 120000, 123, DUMMY_RM_IDENTIFIER,
          Priority.newInstance(0), 0);
    Token containerToken =
        BuilderUtils.newContainerToken(context.getNodeId(),
          containerManager.getContext().getContainerTokenSecretManager()
            .createPassword(containerIdentifier), containerIdentifier);
    StartContainerRequest scRequest =
        StartContainerRequest.newInstance(containerLaunchContext,
          containerToken);
    List<StartContainerRequest> list = new ArrayList<StartContainerRequest>();
    list.add(scRequest);
    StartContainersRequest allRequests =
        StartContainersRequest.newInstance(list);
    containerManager.startContainers(allRequests);
    
    int timeoutSecs = 0;
    while (!processStartFile.exists() && timeoutSecs++ < 20) {
      Thread.sleep(1000);
      LOG.info("Waiting for process start-file to be created");
    }
    Assert.assertTrue("ProcessStartFile doesn't exist!",
        processStartFile.exists());

    // Now verify the contents of the file
    BufferedReader reader =
        new BufferedReader(new FileReader(processStartFile));
    Assert.assertEquals("Hello World!", reader.readLine());
    // Get the pid of the process
    String pid = reader.readLine().trim();
    // No more lines
    Assert.assertEquals(null, reader.readLine());

    BaseContainerManagerTest.waitForContainerState(containerManager, cId,
        ContainerState.COMPLETE, 60);

    List<ContainerId> containerIds = new ArrayList<ContainerId>();
    containerIds.add(cId);
    GetContainerStatusesRequest gcsRequest =
        GetContainerStatusesRequest.newInstance(containerIds);
    ContainerStatus containerStatus =
        containerManager.getContainerStatuses(gcsRequest).getContainerStatuses().get(0);
    Assert.assertEquals(ContainerExitStatus.KILLED_EXCEEDED_VMEM,
        containerStatus.getExitStatus());
    String expectedMsgPattern =
        "Container \\[pid=" + pid + ",containerID=" + cId + "\\] is running "
            + "[0-9]+B beyond the 'VIRTUAL' memory limit. Current usage: "
            + "[0-9.]+ ?[KMGTPE]?B of [0-9.]+ ?[KMGTPE]?B physical memory used; "
            + "[0-9.]+ ?[KMGTPE]?B of [0-9.]+ ?[KMGTPE]?B virtual memory used. "
            + "Killing container.\nDump of the process-tree for "
            + cId + " :\n";
    Pattern pat = Pattern.compile(expectedMsgPattern);
    Assert.assertEquals("Expected message pattern is: " + expectedMsgPattern
        + "\n\nObserved message is: " + containerStatus.getDiagnostics(),
        true, pat.matcher(containerStatus.getDiagnostics()).find());

    // Assert that the process is not alive anymore
    Assert.assertFalse("Process is still alive!",
        exec.signalContainer(new ContainerSignalContext.Builder()
            .setUser(user)
            .setPid(pid)
            .setSignal(Signal.NULL)
            .build()));
  }

};