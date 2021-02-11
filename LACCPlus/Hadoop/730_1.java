//,temp,NMLeveldbStateStoreService.java,498,518,temp,NMLeveldbStateStoreService.java,477,496
//,3
public class xxx {
  private Map<LocalResourceProto, Path> loadStartedResources(
      LeveldbIterator iter, String keyPrefix) throws IOException {
    Map<LocalResourceProto, Path> rsrcs =
        new HashMap<LocalResourceProto, Path>();
    while (iter.hasNext()) {
      Entry<byte[],byte[]> entry = iter.peekNext();
      String key = asString(entry.getKey());
      if (!key.startsWith(keyPrefix)) {
        break;
      }

      Path localPath = new Path(key.substring(keyPrefix.length()));
      if (LOG.isDebugEnabled()) {
        LOG.debug("Loading in-progress resource at " + localPath);
      }
      rsrcs.put(LocalResourceProto.parseFrom(entry.getValue()), localPath);
      iter.next();
    }

    return rsrcs;
  }

};