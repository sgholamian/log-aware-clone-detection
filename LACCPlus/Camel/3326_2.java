//,temp,BoxFilesManagerIT.java,264,271,temp,AccountIT.java,64,73
//,3
public class xxx {
    @Test
    public void testReader() throws Exception {
        final ResourceSet<Account> result = requestBody("direct://READER", null);

        assertNotNull(result, "reader result not null");
        result.forEach(account -> {
            assertNotNull(account, "reader result account not null");
            LOG.debug("reader: " + account);
        });
    }

};