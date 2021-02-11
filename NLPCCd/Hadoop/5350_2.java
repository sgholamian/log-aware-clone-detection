//,temp,sample_7321.java,2,17,temp,sample_5130.java,2,17
//,3
public class xxx {
public void dummy_method(){
allocateRequest.setReleaseList(relList);
AllocateResponse allocateResponse = interceptor.allocate(allocateRequest);
Assert.assertNotNull(allocateResponse);
List<Container> containersForReleasedContainerIds = new ArrayList<Container>();
containersForReleasedContainerIds .addAll(allocateResponse.getAllocatedContainers());
int numHeartbeat = 0;
while (containersForReleasedContainerIds.size() < relList.size() && numHeartbeat++ < 10) {
allocateResponse = interceptor.allocate(Records.newRecord(AllocateRequest.class));
Assert.assertNotNull(allocateResponse);
containersForReleasedContainerIds .addAll(allocateResponse.getAllocatedContainers());


log.info("number of containers received in this request");
}
}

};