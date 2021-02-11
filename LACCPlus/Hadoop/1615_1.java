//,temp,TestContainerLaunch.java,1103,1211,temp,TestContainerLaunch.java,757,887
//,3
public class xxx {
  @Test
  public void testKillProcessGroup() throws Exception {
    Assume.assumeTrue(Shell.isSetsidAvailable);
    containerManager.start();

    // Construct the Container-id
    ApplicationId appId = ApplicationId.newInstance(2, 2);
    ApplicationAttemptId appAttemptId =
        ApplicationAttemptId.newInstance(appId, 1);
    ContainerId cId = ContainerId.newContainerId(appAttemptId, 0);
    File processStartFile =
        new File(tmpDir, "pid.txt").getAbsoluteFile();
    File childProcessStartFile =
        new File(tmpDir, "child_pid.txt").getAbsoluteFile();

    // setup a script that can handle sigterm gracefully
    File scriptFile = Shell.appendScriptExtension(tmpDir, "testscript");
    PrintWriter writer = new PrintWriter(new FileOutputStream(scriptFile));
    writer.println("#!/bin/bash\n\n");
    writer.println("echo \"Running testscript for forked process\"");
    writer.println("umask 0");
    writer.println("echo $$ >> " + processStartFile);
    writer.println("while true;\ndo sleep 1s;\ndone > /dev/null 2>&1 &");
    writer.println("echo $! >> " + childProcessStartFile);
    writer.println("while true;\ndo sleep 1s;\ndone");
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

    BufferedReader reader =
          new BufferedReader(new FileReader(processStartFile));
    // Get the pid of the process
    String pid = reader.readLine().trim();
    // No more lines
    Assert.assertEquals(null, reader.readLine());
    reader.close();

    reader =
          new BufferedReader(new FileReader(childProcessStartFile));
    // Get the pid of the child process
    String child = reader.readLine().trim();
    // No more lines
    Assert.assertEquals(null, reader.readLine());
    reader.close();

    LOG.info("Manually killing pid " + pid + ", but not child pid " + child);
    Shell.execCommand(new String[]{"kill", "-9", pid});

    BaseContainerManagerTest.waitForContainerState(containerManager, cId,
        ContainerState.COMPLETE);

    Assert.assertFalse("Process is still alive!",
        DefaultContainerExecutor.containerIsAlive(pid));

    List<ContainerId> containerIds = new ArrayList<ContainerId>();
    containerIds.add(cId);

    GetContainerStatusesRequest gcsRequest =
        GetContainerStatusesRequest.newInstance(containerIds);

    ContainerStatus containerStatus =
        containerManager.getContainerStatuses(gcsRequest)
            .getContainerStatuses().get(0);
    Assert.assertEquals(ExitCode.FORCE_KILLED.getExitCode(),
        containerStatus.getExitStatus());
  }

};