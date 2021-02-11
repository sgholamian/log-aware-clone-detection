//,temp,KeyValueHandler.java,583,613,temp,KeyValueHandler.java,549,578
//,3
public class xxx {
  ContainerCommandResponseProto handleReadChunk(
      ContainerCommandRequestProto request, KeyValueContainer kvContainer) {

    if (!request.hasReadChunk()) {
      LOG.debug("Malformed Read Chunk request. trace ID: {}",
          request.getTraceID());
      return ContainerUtils.malformedRequest(request);
    }

    ChunkInfo chunkInfo;
    byte[] data;
    try {
      BlockID blockID = BlockID.getFromProtobuf(
          request.getReadChunk().getBlockID());
      chunkInfo = ChunkInfo.getFromProtoBuf(request.getReadChunk()
          .getChunkData());
      Preconditions.checkNotNull(chunkInfo);

      data = chunkManager.readChunk(kvContainer, blockID, chunkInfo);
      metrics.incContainerBytesStats(Type.ReadChunk, data.length);
    } catch (StorageContainerException ex) {
      return ContainerUtils.logAndReturnError(LOG, ex, request);
    } catch (IOException ex) {
      return ContainerUtils.logAndReturnError(LOG,
          new StorageContainerException("Read Chunk failed", ex, IO_EXCEPTION),
          request);
    }

    return ChunkUtils.getReadChunkResponse(request, data, chunkInfo);
  }

};