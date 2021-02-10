//,temp,CompressDecompressTester.java,270,281,temp,CompressDecompressTester.java,216,227
//,3
public class xxx {
      private boolean checkSetInputArrayIndexOutOfBoundsException(
          Decompressor decompressor) {
        try {
          decompressor.setInput(new byte[] { (byte) 0 }, 0, -1);
        } catch (ArrayIndexOutOfBoundsException e) {
          return true;
        } catch (Exception e) {
          logger.error(joiner.join(decompressor.getClass().getCanonicalName(),
              "checkNullPointerException error !!!"));
        }
        return false;
      }

};