//,temp,ContainerTestHelper.java,505,521,temp,ContainerTestHelper.java,360,374
//,3
public class xxx {
  public static ContainerCommandRequestProto getDeleteKeyRequest(
      Pipeline pipeline, ContainerProtos.PutKeyRequestProto putKeyRequest) {
    ContainerProtos.DatanodeBlockID blockID = putKeyRequest.getKeyData()
        .getBlockID();
    LOG.trace("deleteKey: name={}", blockID);
    ContainerProtos.DeleteKeyRequestProto.Builder delRequest =
        ContainerProtos.DeleteKeyRequestProto.newBuilder();
    delRequest.setBlockID(blockID);
    ContainerCommandRequestProto.Builder request =
        ContainerCommandRequestProto.newBuilder();
    request.setCmdType(ContainerProtos.Type.DeleteKey);
    request.setContainerID(blockID.getContainerID());
    request.setDeleteKey(delRequest);
    request.setTraceID(UUID.randomUUID().toString());
    request.setDatanodeUuid(pipeline.getLeader().getUuidString());
    return request.build();
  }

};