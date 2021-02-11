//,temp,WALUtil.java,101,111,temp,WALUtil.java,86,95
//,3
public class xxx {
  public static WALKeyImpl writeFlushMarker(WAL wal, NavigableMap<byte[], Integer> replicationScope,
      RegionInfo hri, final FlushDescriptor f, boolean sync, MultiVersionConcurrencyControl mvcc)
          throws IOException {
    WALKeyImpl walKey = doFullAppendTransaction(wal, replicationScope, hri,
        WALEdit.createFlushWALEdit(hri, f), mvcc, sync);
    if (LOG.isTraceEnabled()) {
      LOG.trace("Appended flush marker " + TextFormat.shortDebugString(f));
    }
    return walKey;
  }

};