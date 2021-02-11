//,temp,sample_1508.java,2,17,temp,sample_4033.java,2,17
//,3
public class xxx {
public void dummy_method(){
writeScriptFile(fileWriterOld, "Hello World!", startFile, cId, false);
ContainerLaunchContext containerLaunchContext = prepareContainerLaunchContext(scriptFileOld, "dest_file", false, 4);
StartContainerRequest scRequest = StartContainerRequest.newInstance(containerLaunchContext, createContainerToken(cId, DUMMY_RM_IDENTIFIER, context.getNodeId(), user, context.getContainerTokenSecretManager()));
List<StartContainerRequest> list = new ArrayList<>();
list.add(scRequest);
StartContainersRequest allRequests = StartContainersRequest.newInstance(list);
containerManager.startContainers(allRequests);
int timeoutSecs = 0;
while (!startFile.exists() && timeoutSecs++ < 20) {
Thread.sleep(1000);


log.info("waiting for process start file to be created");
}
}

};