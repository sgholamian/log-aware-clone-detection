//,temp,CompressDecompressTester.java,242,254,temp,CompressDecompressTester.java,188,200
//,3
public class xxx {
      private boolean checkCompressArrayIndexOutOfBoundsException(
          Compressor compressor, byte[] rawData) {
        try {
          compressor.setInput(rawData, 0, rawData.length);
          compressor.compress(new byte[rawData.length], 0, -1);
        } catch (ArrayIndexOutOfBoundsException e) {
          return true;
        } catch (Exception e) {
          logger.error(joiner.join(compressor.getClass().getCanonicalName(),
              "checkCompressArrayIndexOutOfBoundsException error !!!"));
        }
        return false;
      }

};