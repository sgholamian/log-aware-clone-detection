//,temp,AccountIT.java,45,52,temp,BoxTasksManagerIT.java,134,142
//,3
public class xxx {
    @Test
    public void testFetcher() throws Exception {
        final Account result = requestBody("direct://FETCHER", null);

        assertNotNull(result, "fetcher result not null");
        assertNotNull(result.getSid(), "fetcher result sid not null");
        LOG.debug("fetcher: " + result);
    }

};