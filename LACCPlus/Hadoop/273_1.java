//,temp,OpenFileCtxCache.java,198,219,temp,NMContainerTokenSecretManager.java,214,234
//,3
public class xxx {
  void cleanAll() {
    ArrayList<OpenFileCtx> cleanedContext = new ArrayList<OpenFileCtx>();
    synchronized (this) {
      Iterator<Entry<FileHandle, OpenFileCtx>> it = openFileMap.entrySet()
          .iterator();
      if (LOG.isTraceEnabled()) {
        LOG.trace("openFileMap size:" + openFileMap.size());
      }

      while (it.hasNext()) {
        Entry<FileHandle, OpenFileCtx> pairs = it.next();
        OpenFileCtx ctx = pairs.getValue();
        it.remove();
        cleanedContext.add(ctx);
      }
    }

    // Invoke the cleanup outside the lock
    for (OpenFileCtx ofc : cleanedContext) {
      ofc.cleanup();
    }
  }

};