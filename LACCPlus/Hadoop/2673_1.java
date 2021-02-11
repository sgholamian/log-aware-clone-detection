//,temp,SecureStorageInterfaceImpl.java,127,133,temp,LocalJobRunner.java,733,736
//,3
public class xxx {
  @Override
  public void createBlobClient(URI baseUri, StorageCredentials credentials) {
    String errorMsg = "createBlobClient is an invalid operation in SAS "
        + "Key Mode";
    LOG.error(errorMsg);
    throw new UnsupportedOperationException(errorMsg);
  }

};