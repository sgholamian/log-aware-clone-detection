//,temp,CompressDecompressTester.java,256,268,temp,CompressDecompressTester.java,202,214
//,3
public class xxx {
      private boolean checkCompressNullPointerException(
          Decompressor decompressor, byte[] rawData) {
        try {
          decompressor.setInput(rawData, 0, rawData.length);
          decompressor.decompress(null, 0, 1);
        } catch (NullPointerException npe) {
          return true;
        } catch (Exception ex) {
          logger.error(joiner.join(decompressor.getClass().getCanonicalName(),
              "checkCompressNullPointerException error !!!"));
        }
        return false;
      }

};