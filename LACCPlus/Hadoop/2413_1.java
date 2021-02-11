//,temp,ITestS3AEncryptionAlgorithmValidation.java,115,140,temp,ITestS3AEncryptionAlgorithmValidation.java,64,89
//,3
public class xxx {
  @Test
  public void testEncryptionAlgorithmSSES3WithEncryptionKey() throws
    Throwable {
    //skip tests if they aren't enabled
    assumeEnabled();
    intercept(IOException.class, S3AUtils.SSE_S3_WITH_KEY_ERROR, () -> {

        Configuration conf = super.createConfiguration();
        //SSE-S3 cannot be configured with an encryption key
        conf.set(Constants.SERVER_SIDE_ENCRYPTION_ALGORITHM,
            S3AEncryptionMethods.SSE_S3.getMethod());
        conf.set(Constants.SERVER_SIDE_ENCRYPTION_KEY,
            "4niV/jPK5VFRHY+KNb6wtqYd4xXyMgdJ9XQJpcQUVbs=");
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