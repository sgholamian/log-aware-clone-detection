//,temp,ContainerTestHelper.java,434,457,temp,ContainerTestHelper.java,202,229
//,3
public class xxx {
  public static ContainerCommandRequestProto getPutKeyRequest(
      Pipeline pipeline, ContainerProtos.WriteChunkRequestProto writeRequest) {
    LOG.trace("putKey: {} to pipeline={}",
        writeRequest.getBlockID());

    ContainerProtos.PutKeyRequestProto.Builder putRequest =
        ContainerProtos.PutKeyRequestProto.newBuilder();

    KeyData keyData = new KeyData(
        BlockID.getFromProtobuf(writeRequest.getBlockID()));
    List<ContainerProtos.ChunkInfo> newList = new LinkedList<>();
    newList.add(writeRequest.getChunkData());
    keyData.setChunks(newList);
    putRequest.setKeyData(keyData.getProtoBufMessage());

    ContainerCommandRequestProto.Builder request =
        ContainerCommandRequestProto.newBuilder();
    request.setCmdType(ContainerProtos.Type.PutKey);
    request.setContainerID(keyData.getContainerID());
    request.setPutKey(putRequest);
    request.setTraceID(UUID.randomUUID().toString());
    request.setDatanodeUuid(pipeline.getLeader().getUuidString());
    return request.build();
  }

};