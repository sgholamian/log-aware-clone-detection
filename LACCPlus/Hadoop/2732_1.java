//,temp,KeyValueHandler.java,519,544,temp,KeyValueHandler.java,487,514
//,3
public class xxx {
  ContainerCommandResponseProto handleDeleteKey(
      ContainerCommandRequestProto request, KeyValueContainer kvContainer) {

    if (!request.hasDeleteKey()) {
      LOG.debug("Malformed Delete Key request. trace ID: {}",
          request.getTraceID());
      return ContainerUtils.malformedRequest(request);
    }

    try {
      checkContainerOpen(kvContainer);

      BlockID blockID = BlockID.getFromProtobuf(
          request.getDeleteKey().getBlockID());

      keyManager.deleteKey(kvContainer, blockID);
    } catch (StorageContainerException ex) {
      return ContainerUtils.logAndReturnError(LOG, ex, request);
    } catch (IOException ex) {
      return ContainerUtils.logAndReturnError(LOG,
          new StorageContainerException("Delete Key failed", ex, IO_EXCEPTION),
          request);
    }

    return KeyUtils.getKeyResponseSuccess(request);
  }

};