//,temp,OpenFileCtxCache.java,146,183,temp,PendingReplicationBlocks.java,232,253
//,3
public class xxx {
  @VisibleForTesting
  void scan(long streamTimeout) {
    ArrayList<OpenFileCtx> ctxToRemove = new ArrayList<OpenFileCtx>();
    Iterator<Entry<FileHandle, OpenFileCtx>> it = openFileMap.entrySet()
        .iterator();
    if (LOG.isTraceEnabled()) {
      LOG.trace("openFileMap size:" + openFileMap.size());
    }

    while (it.hasNext()) {
      Entry<FileHandle, OpenFileCtx> pairs = it.next();
      FileHandle handle = pairs.getKey();
      OpenFileCtx ctx = pairs.getValue();
      if (!ctx.streamCleanup(handle.getFileId(), streamTimeout)) {
        continue;
      }

      // Check it again inside lock before removing
      synchronized (this) {
        OpenFileCtx ctx2 = openFileMap.get(handle);
        if (ctx2 != null) {
          if (ctx2.streamCleanup(handle.getFileId(), streamTimeout)) {
            openFileMap.remove(handle);
            if (LOG.isDebugEnabled()) {
              LOG.debug("After remove stream " + handle.getFileId()
                  + ", the stream number:" + openFileMap.size());
            }
            ctxToRemove.add(ctx2);
          }
        }
      }
    }

    // Invoke the cleanup outside the lock
    for (OpenFileCtx ofc : ctxToRemove) {
      ofc.cleanup();
    }
  }

};