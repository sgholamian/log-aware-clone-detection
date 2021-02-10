//,temp,TestDataNodeTcpNoDelay.java,213,220,temp,TestFsDatasetCache.java,345,356
//,3
public class xxx {
      @Override
      public void mlock(String identifier,
          ByteBuffer mmap, long length) throws IOException {
        if (seenIdentifiers.contains(identifier)) {
          // mlock succeeds the second time.
          LOG.info("mlocking " + identifier);
          return;
        }
        seenIdentifiers.add(identifier);
        throw new IOException("injecting IOException during mlock of " +
            identifier);
      }

};