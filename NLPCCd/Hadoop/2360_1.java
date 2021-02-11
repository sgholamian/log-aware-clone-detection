//,temp,sample_1508.java,2,17,temp,sample_4033.java,2,17
//,3
public class xxx {
public void dummy_method(){
long createTime = 1234;
Token containerToken = createContainerToken(cId, priority, createTime);
StartContainerRequest scRequest = StartContainerRequest.newInstance(containerLaunchContext, containerToken);
List<StartContainerRequest> list = new ArrayList<StartContainerRequest>();
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