//,temp,ContainerTestHelper.java,505,521,temp,ContainerTestHelper.java,360,374
//,3
public class xxx {
  public static ContainerCommandRequestProto getCreateContainerRequest(
      long containerID, Pipeline pipeline) throws IOException {
    LOG.trace("addContainer: {}", containerID);

    ContainerCommandRequestProto.Builder request =
        ContainerCommandRequestProto.newBuilder();
    request.setCmdType(ContainerProtos.Type.CreateContainer);
    request.setContainerID(containerID);
    request.setCreateContainer(
        ContainerProtos.CreateContainerRequestProto.getDefaultInstance());
    request.setTraceID(UUID.randomUUID().toString());
    request.setDatanodeUuid(pipeline.getLeader().getUuidString());

    return request.build();
  }

};