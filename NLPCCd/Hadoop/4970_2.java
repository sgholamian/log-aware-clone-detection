//,temp,sample_1507.java,2,17,temp,sample_4034.java,2,17
//,3
public class xxx {
public void dummy_method(){
commands.add(scriptFile.getAbsolutePath());
containerLaunchContext.setCommands(commands);
StartContainerRequest scRequest = StartContainerRequest.newInstance( containerLaunchContext, createContainerToken(cId, DUMMY_RM_IDENTIFIER, context.getNodeId(), user, context.getContainerTokenSecretManager()));
List<StartContainerRequest> list = new ArrayList<>();
list.add(scRequest);
StartContainersRequest allRequests = StartContainersRequest.newInstance(list);
containerManager.startContainers(allRequests);
int timeoutSecs = 0;
while (!processStartFile.exists() && timeoutSecs++ < 20) {
Thread.sleep(1000);


log.info("waiting for process start file to be created");
}
}

};