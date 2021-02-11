//,temp,EditLogTailer_after_fix.java,199,252,temp,EditLogTailer.java,199,252
//,2
public class xxx {
  @VisibleForTesting
  void doTailEdits() throws IOException, InterruptedException {
    // Write lock needs to be interruptible here because the 
    // transitionToActive RPC takes the write lock before calling
    // tailer.stop() -- so if we're not interruptible, it will
    // deadlock.
    namesystem.writeLockInterruptibly();
    try {
      FSImage image = namesystem.getFSImage();

      long lastTxnId = image.getLastAppliedTxId();
      
      if (LOG.isDebugEnabled()) {
        LOG.debug("lastTxnId: " + lastTxnId);
      }
      Collection<EditLogInputStream> streams;
      try {
        streams = editLog.selectInputStreams(lastTxnId + 1, 0, null, false);
      } catch (IOException ioe) {
        // This is acceptable. If we try to tail edits in the middle of an edits
        // log roll, i.e. the last one has been finalized but the new inprogress
        // edits file hasn't been started yet.
        LOG.warn("Edits tailer failed to find any streams. Will try again " +
            "later.", ioe);
        return;
      }
      if (LOG.isDebugEnabled()) {
        LOG.debug("edit streams to load from: " + streams.size());
      }
      
      // Once we have streams to load, errors encountered are legitimate cause
      // for concern, so we don't catch them here. Simple errors reading from
      // disk are ignored.
      long editsLoaded = 0;
      try {
        editsLoaded = image.loadEdits(streams, namesystem);
      } catch (EditLogInputException elie) {
        editsLoaded = elie.getNumEditsLoaded();
        throw elie;
      } finally {
        if (editsLoaded > 0 || LOG.isDebugEnabled()) {
          LOG.info(String.format("Loaded %d edits starting from txid %d ",
              editsLoaded, lastTxnId));
        }
      }

      if (editsLoaded > 0) {
        lastLoadTimeMs = monotonicNow();
      }
      lastLoadedTxnId = image.getLastAppliedTxId();
    } finally {
      namesystem.writeUnlock();
    }
  }

};