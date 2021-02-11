//,temp,TestContainerManager.java,261,362,temp,TestContainerLaunch.java,757,887
//,3
public class xxx {
  private void internalKillTest(boolean delayed) throws Exception {
    conf.setLong(YarnConfiguration.NM_SLEEP_DELAY_BEFORE_SIGKILL_MS,
      delayed ? 1000 : 0);
    containerManager.start();

    // ////// Construct the Container-id
    ApplicationId appId = ApplicationId.newInstance(1, 1);
    ApplicationAttemptId appAttemptId =
        ApplicationAttemptId.newInstance(appId, 1);
    ContainerId cId = ContainerId.newContainerId(appAttemptId, 0);
    File processStartFile =
        new File(tmpDir, "pid.txt").getAbsoluteFile();

    // setup a script that can handle sigterm gracefully
    File scriptFile = Shell.appendScriptExtension(tmpDir, "testscript");
    PrintWriter writer = new PrintWriter(new FileOutputStream(scriptFile));
    if (Shell.WINDOWS) {
      writer.println("@echo \"Running testscript for delayed kill\"");
      writer.println("@echo \"Writing pid to start file\"");
      writer.println("@echo " + cId + "> " + processStartFile);
      writer.println("@ping -n 100 127.0.0.1 >nul");
    } else {
      writer.println("#!/bin/bash\n\n");
      writer.println("echo \"Running testscript for delayed kill\"");
      writer.println("hello=\"Got SIGTERM\"");
      writer.println("umask 0");
      writer.println("trap \"echo $hello >> " + processStartFile + "\" SIGTERM");
      writer.println("echo \"Writing pid to start file\"");
      writer.println("echo $$ >> " + processStartFile);
      writer.println("while true; do\nsleep 1s;\ndone");
    }
    writer.close();
    FileUtil.setExecutable(scriptFile, true);

    ContainerLaunchContext containerLaunchContext = 
        recordFactory.newRecordInstance(ContainerLaunchContext.class);

    // upload the script file so that the container can run it
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
    String destinationFile = "dest_file.sh";
    Map<String, LocalResource> localResources = 
        new HashMap<String, LocalResource>();
    localResources.put(destinationFile, rsrc_alpha);
    containerLaunchContext.setLocalResources(localResources);

    // set up the rest of the container
    List<String> commands = Arrays.asList(Shell.getRunScriptCommand(scriptFile));
    containerLaunchContext.setCommands(commands);
    Priority priority = Priority.newInstance(10);
    long createTime = 1234;
    Token containerToken = createContainerToken(cId, priority, createTime);

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

    NMContainerStatus nmContainerStatus =
        containerManager.getContext().getContainers().get(cId)
          .getNMContainerStatus();
    Assert.assertEquals(priority, nmContainerStatus.getPriority());

    // Now test the stop functionality.
    List<ContainerId> containerIds = new ArrayList<ContainerId>();
    containerIds.add(cId);
    StopContainersRequest stopRequest =
        StopContainersRequest.newInstance(containerIds);
    containerManager.stopContainers(stopRequest);

    BaseContainerManagerTest.waitForContainerState(containerManager, cId,
        ContainerState.COMPLETE);

    // if delayed container stop sends a sigterm followed by a sigkill
    // otherwise sigkill is sent immediately 
    GetContainerStatusesRequest gcsRequest =
        GetContainerStatusesRequest.newInstance(containerIds);
    
    ContainerStatus containerStatus = 
        containerManager.getContainerStatuses(gcsRequest)
          .getContainerStatuses().get(0);
    Assert.assertEquals(ContainerExitStatus.KILLED_BY_APPMASTER,
        containerStatus.getExitStatus());

    // Now verify the contents of the file.  Script generates a message when it
    // receives a sigterm so we look for that.  We cannot perform this check on
    // Windows, because the process is not notified when killed by winutils.
    // There is no way for the process to trap and respond.  Instead, we can
    // verify that the job object with ID matching container ID no longer exists.
    if (Shell.WINDOWS || !delayed) {
      Assert.assertFalse("Process is still alive!",
        DefaultContainerExecutor.containerIsAlive(cId.toString()));
    } else {
      BufferedReader reader =
          new BufferedReader(new FileReader(processStartFile));

      boolean foundSigTermMessage = false;
      while (true) {
        String line = reader.readLine();
        if (line == null) {
          break;
        }
        if (line.contains("SIGTERM")) {
          foundSigTermMessage = true;
          break;
        }
      }
      Assert.assertTrue("Did not find sigterm message", foundSigTermMessage);
      reader.close();
    }
  }

};