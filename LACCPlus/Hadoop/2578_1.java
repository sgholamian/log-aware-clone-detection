//,temp,CompressDecompressTester.java,216,227,temp,CompressDecompressTester.java,202,214
//,3
public class xxx {
      private boolean checkSetInputNullPointerException(
          Decompressor decompressor) {
        try {
          decompressor.setInput(null, 0, 1);
        } catch (NullPointerException npe) {
          return true;
        } catch (Exception ex) {
          logger.error(joiner.join(decompressor.getClass().getCanonicalName(),
              "checkSetInputNullPointerException error !!!"));
        }
        return false;
      }

};