//,temp,TestContainerManager.java,261,362,temp,TestContainerLaunch.java,1103,1211
//,3
public class xxx {
  @Test
  public void testContainerLaunchAndStop() throws IOException,
      InterruptedException, YarnException {
    containerManager.start();

    File scriptFile = Shell.appendScriptExtension(tmpDir, "scriptFile");
    PrintWriter fileWriter = new PrintWriter(scriptFile);
    File processStartFile =
        new File(tmpDir, "start_file.txt").getAbsoluteFile();

    // ////// Construct the Container-id
    ContainerId cId = createContainerId(0);

    if (Shell.WINDOWS) {
      fileWriter.println("@echo Hello World!> " + processStartFile);
      fileWriter.println("@echo " + cId + ">> " + processStartFile);
      fileWriter.println("@ping -n 100 127.0.0.1 >nul");
    } else {
      fileWriter.write("\numask 0"); // So that start file is readable by the test
      fileWriter.write("\necho Hello World! > " + processStartFile);
      fileWriter.write("\necho $$ >> " + processStartFile);
      fileWriter.write("\nexec sleep 100");
    }
    fileWriter.close();

    ContainerLaunchContext containerLaunchContext = 
        recordFactory.newRecordInstance(ContainerLaunchContext.class);

    URL resource_alpha =
        ConverterUtils.getYarnUrlFromPath(localFS
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
    List<String> commands = Arrays.asList(Shell.getRunScriptCommand(scriptFile));
    containerLaunchContext.setCommands(commands);

    StartContainerRequest scRequest =
        StartContainerRequest.newInstance(containerLaunchContext,
          createContainerToken(cId,
            DUMMY_RM_IDENTIFIER, context.getNodeId(), user,
            context.getContainerTokenSecretManager()));
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

    // Now test the stop functionality.

    // Assert that the process is alive
    Assert.assertTrue("Process is not alive!",
      DefaultContainerExecutor.containerIsAlive(pid));
    // Once more
    Assert.assertTrue("Process is not alive!",
      DefaultContainerExecutor.containerIsAlive(pid));

    List<ContainerId> containerIds = new ArrayList<ContainerId>();
    containerIds.add(cId);
    StopContainersRequest stopRequest =
        StopContainersRequest.newInstance(containerIds);
    containerManager.stopContainers(stopRequest);
    BaseContainerManagerTest.waitForContainerState(containerManager, cId,
        ContainerState.COMPLETE);
    
    GetContainerStatusesRequest gcsRequest =
        GetContainerStatusesRequest.newInstance(containerIds);
    ContainerStatus containerStatus = 
        containerManager.getContainerStatuses(gcsRequest).getContainerStatuses().get(0);
    int expectedExitCode = ContainerExitStatus.KILLED_BY_APPMASTER;
    Assert.assertEquals(expectedExitCode, containerStatus.getExitStatus());

    // Assert that the process is not alive anymore
    Assert.assertFalse("Process is still alive!",
      DefaultContainerExecutor.containerIsAlive(pid));
  }

};