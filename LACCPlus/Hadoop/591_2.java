//,temp,Compression.java,305,329,temp,Compression.java,271,294
//,2
public class xxx {
    public Compressor getCompressor() throws IOException {
      CompressionCodec codec = getCodec();
      if (codec != null) {
        Compressor compressor = CodecPool.getCompressor(codec);
        if (compressor != null) {
          if (compressor.finished()) {
            // Somebody returns the compressor to CodecPool but is still using
            // it.
            LOG.warn("Compressor obtained from CodecPool already finished()");
          } else {
            if(LOG.isDebugEnabled()) {
              LOG.debug("Got a compressor: " + compressor.hashCode());
            }
          }
          /**
           * Following statement is necessary to get around bugs in 0.18 where a
           * compressor is referenced after returned back to the codec pool.
           */
          compressor.reset();
        }
        return compressor;
      }
      return null;
    }

};