//,temp,ITestS3AEncryptionAlgorithmValidation.java,115,140,temp,ITestS3AEncryptionAlgorithmValidation.java,64,89
//,3
public class xxx {
  @Test
  public void testEncryptionAlgorithmSSECWithNoEncryptionKey() throws
    Throwable {
    //skip tests if they aren't enabled
    assumeEnabled();
    intercept(IllegalArgumentException.class, "The value of property " +
        Constants.SERVER_SIDE_ENCRYPTION_KEY + " must not be null", () -> {

        Configuration conf = super.createConfiguration();
        //SSE-C must be configured with an encryption key
        conf.set(Constants.SERVER_SIDE_ENCRYPTION_ALGORITHM,
            S3AEncryptionMethods.SSE_C.getMethod());
        conf.set(Constants.SERVER_SIDE_ENCRYPTION_KEY, null);
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