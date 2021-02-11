//,temp,ContainerTestHelper.java,434,457,temp,ContainerTestHelper.java,202,229
//,3
public class xxx {
  public static ContainerCommandRequestProto getWriteChunkRequest(
      Pipeline pipeline, BlockID blockID, int datalen)
      throws IOException, NoSuchAlgorithmException {
    LOG.trace("writeChunk {} (blockID={}) to pipeline=",
        datalen, blockID, pipeline);
    ContainerProtos.WriteChunkRequestProto.Builder writeRequest =
        ContainerProtos.WriteChunkRequestProto
            .newBuilder();

    writeRequest.setBlockID(blockID.getDatanodeBlockIDProtobuf());

    byte[] data = getData(datalen);
    ChunkInfo info = getChunk(blockID.getLocalID(), 0, 0, datalen);
    setDataChecksum(info, data);

    writeRequest.setChunkData(info.getProtoBufMessage());
    writeRequest.setData(ByteString.copyFrom(data));

    ContainerCommandRequestProto.Builder request =
        ContainerCommandRequestProto.newBuilder();
    request.setCmdType(ContainerProtos.Type.WriteChunk);
    request.setContainerID(blockID.getContainerID());
    request.setWriteChunk(writeRequest);
    request.setTraceID(UUID.randomUUID().toString());
    request.setDatanodeUuid(pipeline.getLeader().getUuidString());

    return request.build();
  }

};