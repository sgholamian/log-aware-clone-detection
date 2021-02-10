//,temp,CompressDecompressTester.java,242,254,temp,CompressDecompressTester.java,229,240
//,3
public class xxx {
      private boolean checkSetInputArrayIndexOutOfBoundsException(
          Compressor compressor) {
        try {
          compressor.setInput(new byte[] { (byte) 0 }, 0, -1);
        } catch (ArrayIndexOutOfBoundsException e) {
          return true;
        } catch (Exception e) {
          logger.error(joiner.join(compressor.getClass().getCanonicalName(),
              "checkSetInputArrayIndexOutOfBoundsException error !!!"));
        }
        return false;
      }

};