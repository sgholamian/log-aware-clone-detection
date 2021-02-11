//,temp,KeyValueHandler.java,583,613,temp,KeyValueHandler.java,549,578
//,3
public class xxx {
  ContainerCommandResponseProto handleDeleteChunk(
      ContainerCommandRequestProto request, KeyValueContainer kvContainer) {

    if (!request.hasDeleteChunk()) {
      LOG.debug("Malformed Delete Chunk request. trace ID: {}",
          request.getTraceID());
      return ContainerUtils.malformedRequest(request);
    }

    try {
      checkContainerOpen(kvContainer);

      BlockID blockID = BlockID.getFromProtobuf(
          request.getDeleteChunk().getBlockID());
      ContainerProtos.ChunkInfo chunkInfoProto = request.getDeleteChunk()
          .getChunkData();
      ChunkInfo chunkInfo = ChunkInfo.getFromProtoBuf(chunkInfoProto);
      Preconditions.checkNotNull(chunkInfo);

      chunkManager.deleteChunk(kvContainer, blockID, chunkInfo);
      openContainerBlockMap.removeChunk(blockID, chunkInfoProto);
    } catch (StorageContainerException ex) {
      return ContainerUtils.logAndReturnError(LOG, ex, request);
    } catch (IOException ex) {
      return ContainerUtils.logAndReturnError(LOG,
          new StorageContainerException("Delete Chunk failed", ex,
              IO_EXCEPTION), request);
    }

    return ChunkUtils.getChunkResponseSuccess(request);
  }

};