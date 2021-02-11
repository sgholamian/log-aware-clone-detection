//,temp,CompressDecompressTester.java,270,281,temp,CompressDecompressTester.java,229,240
//,2
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