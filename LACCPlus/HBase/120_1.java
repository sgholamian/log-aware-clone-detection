//,temp,WALUtil.java,101,111,temp,WALUtil.java,86,95
//,3
public class xxx {
  public static WALKeyImpl writeRegionEventMarker(WAL wal,
      NavigableMap<byte[], Integer> replicationScope, RegionInfo hri,
      final RegionEventDescriptor r, final MultiVersionConcurrencyControl mvcc)
  throws IOException {
    WALKeyImpl walKey = writeMarker(wal, replicationScope, hri,
        WALEdit.createRegionEventWALEdit(hri, r), mvcc);
    if (LOG.isTraceEnabled()) {
      LOG.trace("Appended region event marker " + TextFormat.shortDebugString(r));
    }
    return walKey;
  }

};