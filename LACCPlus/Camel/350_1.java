//,temp,DriveFilesIT.java,115,121,temp,CreditCardVerificationGatewayIT.java,51,60
//,3
public class xxx {
    @Test
    public void testInsert1() throws Exception {
        File result = uploadTestFile();

        assertNotNull(result, "insert result");
        LOG.debug("insert: " + result);
    }

};