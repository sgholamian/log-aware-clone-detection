//,temp,CompressDecompressTester.java,256,268,temp,CompressDecompressTester.java,202,214
//,3
public class xxx {
      private boolean checkCompressArrayIndexOutOfBoundsException(
          Decompressor decompressor, byte[] rawData) {
        try {
          decompressor.setInput(rawData, 0, rawData.length);
          decompressor.decompress(new byte[rawData.length], 0, -1);
        } catch (ArrayIndexOutOfBoundsException e) {
          return true;
        } catch (Exception e) {
          logger.error(joiner.join(decompressor.getClass().getCanonicalName(),
              "checkCompressArrayIndexOutOfBoundsException error !!!"));
        }
        return false;
      }

};