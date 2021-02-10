//,temp,CompressDecompressTester.java,216,227,temp,CompressDecompressTester.java,188,200
//,3
public class xxx {
      private boolean checkCompressNullPointerException(Compressor compressor,
          byte[] rawData) {
        try {
          compressor.setInput(rawData, 0, rawData.length);
          compressor.compress(null, 0, 1);
        } catch (NullPointerException npe) {
          return true;
        } catch (Exception ex) {
          logger.error(joiner.join(compressor.getClass().getCanonicalName(),
              "checkCompressNullPointerException error !!!"));
        }
        return false;
      }

};