//,temp,CompressDecompressTester.java,270,281,temp,CompressDecompressTester.java,176,186
//,3
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