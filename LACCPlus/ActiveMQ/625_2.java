//,temp,MultipleTestsWithEmbeddedBrokerTest.java,39,47,temp,FilesystemBlobTest.java,44,56
//,3
public class xxx {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        // replace \ with / to let it work on windows too
        String fileUrl = "file:///" +tmpDir.replaceAll("\\\\", "/");
        LOG.info("Using file: " + fileUrl);
        bindAddress = "vm://localhost?jms.blobTransferPolicy.defaultUploadUrl=" + fileUrl;

        connectionFactory = createConnectionFactory();

        connection = createConnection();
        connection.start();
    }

};