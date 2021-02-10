//,temp,TestContainersMonitor.java,242,354,temp,TestContainerManager.java,1974,2051
//,3
public class xxx {
  private void testContainerLaunchAndSignal(SignalContainerCommand command)
      throws IOException, InterruptedException, YarnException {

    Signal signal = ContainerLaunch.translateCommandToSignal(command);
    containerManager.start();

    File scriptFile = new File(tmpDir, "scriptFile.sh");
    PrintWriter fileWriter = new PrintWriter(scriptFile);
    File processStartFile =
        new File(tmpDir, "start_file.txt").getAbsoluteFile();
    fileWriter.write("\numask 0"); // So that start file is readable by the test
    fileWriter.write("\necho Hello World! > " + processStartFile);
    fileWriter.write("\necho $$ >> " + processStartFile);
    fileWriter.write("\nexec sleep 1000s");
    fileWriter.close();

    ContainerLaunchContext containerLaunchContext =
        recordFactory.newRecordInstance(ContainerLaunchContext.class);

    // ////// Construct the Container-id
    ContainerId cId = createContainerId(0);

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
    List<String> commands = new ArrayList<>();
    commands.add("/bin/bash");
    commands.add(scriptFile.getAbsolutePath());
    containerLaunchContext.setCommands(commands);
    StartContainerRequest scRequest =
        StartContainerRequest.newInstance(
            containerLaunchContext,
            createContainerToken(cId, DUMMY_RM_IDENTIFIER, context.getNodeId(),
            user, context.getContainerTokenSecretManager()));
    List<StartContainerRequest> list = new ArrayList<>();
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

    // Simulate NodeStatusUpdaterImpl sending CMgrSignalContainersEvent
    SignalContainerRequest signalReq =
        SignalContainerRequest.newInstance(cId, command);
    List<SignalContainerRequest> reqs = new ArrayList<>();
    reqs.add(signalReq);
    containerManager.handle(new CMgrSignalContainersEvent(reqs));

    final ArgumentCaptor<ContainerSignalContext> signalContextCaptor =
        ArgumentCaptor.forClass(ContainerSignalContext.class);
    if (signal.equals(Signal.NULL)) {
      verify(exec, never()).signalContainer(signalContextCaptor.capture());
    } else {
      verify(exec, timeout(10000).atLeastOnce()).signalContainer(signalContextCaptor.capture());
      ContainerSignalContext signalContext = signalContextCaptor.getAllValues().get(0);
      Assert.assertEquals(cId, signalContext.getContainer().getContainerId());
      Assert.assertEquals(signal, signalContext.getSignal());
    }
  }

};