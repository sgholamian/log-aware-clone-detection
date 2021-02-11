//,temp,KeyValueHandler.java,519,544,temp,KeyValueHandler.java,487,514
//,3
public class xxx {
  ContainerCommandResponseProto handleGetCommittedBlockLength(
      ContainerCommandRequestProto request, KeyValueContainer kvContainer) {
    if (!request.hasGetCommittedBlockLength()) {
      LOG.debug("Malformed Get Key request. trace ID: {}",
          request.getTraceID());
      return ContainerUtils.malformedRequest(request);
    }

    long blockLength;
    try {
      BlockID blockID = BlockID
          .getFromProtobuf(request.getGetCommittedBlockLength().getBlockID());
      // Check if it really exists in the openContainerBlockMap
      if (openContainerBlockMap.checkIfBlockExists(blockID)) {
        String msg = "Block " + blockID + " is not committed yet.";
        throw new StorageContainerException(msg, BLOCK_NOT_COMMITTED);
      }
      blockLength = keyManager.getCommittedBlockLength(kvContainer, blockID);
    } catch (StorageContainerException ex) {
      return ContainerUtils.logAndReturnError(LOG, ex, request);
    } catch (IOException ex) {
      return ContainerUtils.logAndReturnError(LOG,
          new StorageContainerException("GetCommittedBlockLength failed", ex,
              IO_EXCEPTION), request);
    }

    return KeyUtils.getBlockLengthResponse(request, blockLength);
  }

};