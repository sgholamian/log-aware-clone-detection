//,temp,CompressDecompressTester.java,216,227,temp,CompressDecompressTester.java,176,186
//,2
public class xxx {
      private boolean checkSetInputNullPointerException(Compressor compressor) {
        try {
          compressor.setInput(null, 0, 1);
        } catch (NullPointerException npe) {
          return true;
        } catch (Exception ex) {
          logger.error(joiner.join(compressor.getClass().getCanonicalName(),
              "checkSetInputNullPointerException error !!!"));
        }
        return false;
      }

};