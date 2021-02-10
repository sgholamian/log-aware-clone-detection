//,temp,KeyProcessTemplate.java,182,194,temp,KeyProcessTemplate.java,149,167
//,3
public class xxx {
  public void checkFileLengthMatch(KeyArgs args, StorageHandler fs,
                                   int contentLen, int bytesRead)
      throws IOException, OzoneException {
    if (bytesRead != contentLen) {
      fs.deleteKey(args);
      OzoneException ex = ErrorTable.newError(INCOMPLETE_BODY, args);
      String msg = String.format("Body length mismatch. Expected length : %d" +
          " Found %d", contentLen, bytesRead);
      ex.setMessage(msg);
      LOG.debug(msg);
      throw ex;
    }
  }

};