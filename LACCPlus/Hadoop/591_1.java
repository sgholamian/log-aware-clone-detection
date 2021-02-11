//,temp,Compression.java,305,329,temp,Compression.java,271,294
//,2
public class xxx {
    public Decompressor getDecompressor() throws IOException {
      CompressionCodec codec = getCodec();
      if (codec != null) {
        Decompressor decompressor = CodecPool.getDecompressor(codec);
        if (decompressor != null) {
          if (decompressor.finished()) {
            // Somebody returns the decompressor to CodecPool but is still using
            // it.
            LOG.warn("Deompressor obtained from CodecPool already finished()");
          } else {
            if(LOG.isDebugEnabled()) {
              LOG.debug("Got a decompressor: " + decompressor.hashCode());
            }
          }
          /**
           * Following statement is necessary to get around bugs in 0.18 where a
           * decompressor is referenced after returned back to the codec pool.
           */
          decompressor.reset();
        }
        return decompressor;
      }

      return null;
    }

};