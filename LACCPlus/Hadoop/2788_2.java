//,temp,KeyProcessTemplate.java,182,194,temp,KeyProcessTemplate.java,149,167
//,3
public class xxx {
  public void checkFileHashMatch(KeyArgs args, String computedString,
                                 StorageHandler fs, String contentHash)
      throws IOException, OzoneException {
    if (contentHash != null) {
      String contentString =
          new String(Base64.decodeBase64(contentHash), OzoneUtils.ENCODING)
              .trim();

      if (!contentString.equals(computedString)) {
        fs.deleteKey(args);
        OzoneException ex = ErrorTable.newError(BAD_DIGEST, args);
        String msg = String.format("MD5 Digest mismatch. Expected %s Found " +
            "%s", contentString, computedString);
        ex.setMessage(msg);
        LOG.debug(msg);
        throw ex;
      }
    }
  }

};