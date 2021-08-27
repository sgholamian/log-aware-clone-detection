//,temp,AbstractMultiKahaDBDeletionTest.java,159,176,temp,AbstractMultiKahaDBDeletionTest.java,119,136
//,3
public class xxx {
    @Test
    public void testStoreCleanupDeleteDest1First() throws Exception {
        LOG.info("Creating {} first, {} second", dest1, dest2);
        LOG.info("Deleting {} first, {} second", dest1, dest2);

        // Create two destinations
        broker.addDestination(brokerService.getAdminConnectionContext(), dest1, false);
        broker.addDestination(brokerService.getAdminConnectionContext(), dest2, false);

        // remove both destinations
        broker.removeDestination(brokerService.getAdminConnectionContext(), dest1, 100);
        broker.removeDestination(brokerService.getAdminConnectionContext(), dest2, 100);

        //Assert that with no more destinations attached to a store that it has been cleaned up
        Collection<File> storeFiles = FileUtils.listFiles(storeDir, new WildcardFileFilter("db*"), getStoreFileFilter());
        assertEquals("Store files should be deleted", 0, storeFiles.size());

    }

};