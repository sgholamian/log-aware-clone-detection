//,temp,ContainerTestHelper.java,505,521,temp,ContainerTestHelper.java,300,319
//,3
public class xxx {
  public static ContainerCommandRequestProto getReadChunkRequest(
      Pipeline pipeline, ContainerProtos.WriteChunkRequestProto request)
      throws IOException, NoSuchAlgorithmException {
    LOG.trace("readChunk blockID={} from pipeline={}",
        request.getBlockID(), pipeline);

    ContainerProtos.ReadChunkRequestProto.Builder readRequest =
        ContainerProtos.ReadChunkRequestProto.newBuilder();
    readRequest.setBlockID(request.getBlockID());
    readRequest.setChunkData(request.getChunkData());

    ContainerCommandRequestProto.Builder newRequest =
        ContainerCommandRequestProto.newBuilder();
    newRequest.setCmdType(ContainerProtos.Type.ReadChunk);
    newRequest.setContainerID(readRequest.getBlockID().getContainerID());
    newRequest.setReadChunk(readRequest);
    newRequest.setTraceID(UUID.randomUUID().toString());
    newRequest.setDatanodeUuid(pipeline.getLeader().getUuidString());
    return newRequest.build();
  }

};