//,temp,ITestS3AEncryptionAlgorithmValidation.java,91,113,temp,ITestS3AEncryptionAlgorithmValidation.java,41,62
//,3
public class xxx {
  @Test
  public void testEncryptionAlgorithmSSECWithBlankEncryptionKey() throws
    Throwable {
    intercept(IOException.class, S3AUtils.SSE_C_NO_KEY_ERROR, () -> {

        Configuration conf = super.createConfiguration();
        //SSE-C must be configured with an encryption key
        conf.set(Constants.SERVER_SIDE_ENCRYPTION_ALGORITHM,
            S3AEncryptionMethods.SSE_C.getMethod());
        conf.set(Constants.SERVER_SIDE_ENCRYPTION_KEY, "");
        S3AContract contract = (S3AContract) createContract(conf);
        contract.init();
        //extract the test FS
        FileSystem fileSystem = contract.getTestFileSystem();
        assertNotNull("null filesystem", fileSystem);
        URI fsURI = fileSystem.getUri();
        LOG.info("Test filesystem = {} implemented by {}", fsURI, fileSystem);
        assertEquals("wrong filesystem of " + fsURI,
            contract.getScheme(), fsURI.getScheme());
        fileSystem.initialize(fsURI, conf);
        throw new Exception("Do not reach here");
      });
  }

};