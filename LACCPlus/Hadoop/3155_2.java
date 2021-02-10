//,temp,ITestS3AEncryptionAlgorithmValidation.java,91,113,temp,ITestS3AEncryptionAlgorithmValidation.java,41,62
//,3
public class xxx {
  @Test
  public void testEncryptionAlgorithmSetToDES() throws Throwable {
    //skip tests if they aren't enabled
    assumeEnabled();
    intercept(IOException.class, "Unknown Server Side algorithm DES", () -> {

        Configuration conf = super.createConfiguration();
        //DES is an invalid encryption algorithm
        conf.set(Constants.SERVER_SIDE_ENCRYPTION_ALGORITHM, "DES");
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